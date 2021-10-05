package com.moaka.service;

import com.moaka.common.cdn.CdnService;
import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Archive;
import com.moaka.dto.User;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.SectionMapper;
import com.moaka.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    AuthMapper authMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    EncryptionService encryptionService;
    @Autowired
    CdnService cdnService;

    private final JwtTokenProvider jwtTokenProvider;

    public JSONObject login(User params) {
        try {
            JSONObject result = new JSONObject();
            User userInfo = authMapper.login(params);
            ArrayList<String> categoryList = userMapper.retrieveCategoryListByUserNo(userInfo.getNo());
            String message = "";
            boolean isLogin = false;
            if (userInfo != null) {
                System.out.println("로그인 성공");
                isLogin = true;
                message = jwtTokenProvider.createToken(userInfo.getId(), Collections.singletonList("ROLE_USER"), userInfo.getNo(), userInfo.getName(), userInfo.getProfile(), categoryList);
            } else {
                System.out.println("로그인 실패");
                isLogin = false;
                message = "he is not member";
            }
            result.put("isLogin", isLogin);
            result.put("token", message);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    public JSONObject register(User params) throws Exception {
        JSONObject result = new JSONObject();
        // 회원 유저 여부
        User user = userMapper.retrieveUserById(params.getId());
        if (user != null) {
            System.out.println("기존 유저입니다.");
            result.put("isSuccess", false);
        } else {
            System.out.println("기존 유저가 아닙니다.");
            authMapper.register(params);
            String today = getToday();

            Archive archiveInfo = new Archive();
            archiveInfo.setThumbnail("https://moaka-s3.s3.ap-northeast-2.amazonaws.com/logo/moaka_logo.png");
            archiveInfo.setUser_no(params.getNo());
            archiveInfo.setTitle("개인 저장소");
            archiveInfo.setDescription("개인적으로 사용할 수 있는 아카이브입니다.");
            archiveInfo.setPrivacy_type("private");
            archiveInfo.setRegdate(today);

            archiveMapper.insertArchive(archiveInfo);
            archiveMapper.insertArchiveOfGroup(archiveInfo.getUser_no(), archiveInfo.getNo(), today);

            result.put("no", params.getNo());
            result.put("isSuccess", true);
        }
        return result;
    }

    public JSONObject updateUserInfo(User params) throws IOException {
        JSONObject result = new JSONObject();
        try {
            // TODO profile File 값이 널이 아닌 경우 profile 업데이트
            if (params.getProfileFile() != null) {
                // profile 업데이트
                cdnService.FileDelete(params.getProfile());
                String profileUrl = cdnService.FileUpload("user/" + params.getName() + "/profile", params.getProfileFile());
                params.setProfile(profileUrl);
            }

            userMapper.updateUserInfo(params.getNo(), params.getProfile(), params.getName());
            userMapper.deleteUserCategory(params.getNo());
            for (int i = 0; i < params.getCategoryList().size(); i++) {
                userMapper.insertUserCategory(params.getCategoryList().get(i), params.getNo());
            }

            String token = jwtTokenProvider.createToken(params.getId(), Collections.singletonList("ROLE_USER"), params.getNo(), params.getName(), params.getProfile(), params.getCategoryList());

            result.put("isSuccess", true);
            result.put("token", token);
            result.put("profile", params.getProfile());
        } catch (IOException e) {
            e.printStackTrace();
            result.put("isSuccess", false);
        }

        return result;
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
