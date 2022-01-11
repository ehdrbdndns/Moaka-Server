package com.moaka.service;

import com.moaka.common.cdn.S3Uploader;
import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Archive;
import com.moaka.dto.Section;
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
import org.springframework.web.multipart.MultipartFile;

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
    S3Uploader s3Uploader;

    private final JwtTokenProvider jwtTokenProvider;

    public JSONObject login(User params) {
        try {
            JSONObject result = new JSONObject();
            User userInfo = authMapper.login(params);
            String message = "";
            boolean isLogin = false;
            if (userInfo != null) {
                // TODO 로그인 성공
                ArrayList<String> categoryList = userMapper.retrieveCategoryListByUserNo(userInfo.getNo());
                isLogin = true;
                // JWT 토큰 생성
                message = jwtTokenProvider.createToken(userInfo.getId(), Collections.singletonList("ROLE_USER"), userInfo.getNo(), userInfo.getName(), userInfo.getProfile(), categoryList);
            } else {
                // TODO 로그인 실패
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
            // TODO 기존에 가입된 이메일
            result.put("isSuccess", false);
        } else {
            // TODO 기존에 가입되지 않은 이메일
            // 회원가입
            authMapper.register(params);
            for(int i = 0; i < params.getCategoryList().size(); i++) {
                // 사용자가 관심사로 선택한 카테고리 셋팅
                userMapper.insertUserCategory(params.getCategoryList().get(i), params.getNo());
            }
            String today = getToday();

            Archive archiveInfo = new Archive();
            archiveInfo.setThumbnail("https://moaka-s3.s3.ap-northeast-2.amazonaws.com/logo/moaka_logo.png");
            archiveInfo.setUser_no(params.getNo());
            archiveInfo.setTitle("개인 디렉토리");
            archiveInfo.setDescription("자동 생성된 아카이브입니다.");
            archiveInfo.setPrivacy_type("private");
            archiveInfo.setRegdate(today);

            // 사용자의 기본 아카이브 생성
            archiveMapper.insertArchive(archiveInfo);
            archiveMapper.insertArchiveOfGroup(archiveInfo.getUser_no(), archiveInfo.getNo(), today);

            Section sectionInfo = new Section();
            sectionInfo.setTitle("저장소");
            sectionInfo.setArchive_no(archiveInfo.getNo());
            sectionInfo.setDescription("기본적으로 생성된 저장소입니다.");
            sectionInfo.setRegdate(getToday());

            // 사용자의 기본 아카이브의 기본 섹션 생성
            sectionMapper.insertSection(sectionInfo);

            result.put("no", params.getNo());
            result.put("isSuccess", true);
        }
        return result;
    }

    public JSONObject updateUserInfo(User params) throws IOException {
        JSONObject result = new JSONObject();
        try {
            // profile File 값이 널이 아닌 경우 profile 업데이트
            if (params.getProfileFile() != null) {
                // profile 업데이트
                s3Uploader.delete(params.getProfile());
                String profileUrl = s3Uploader.upload(params.getProfileFile(), "user/" + params.getName() + "/profile");
                params.setProfile(profileUrl);
            }

            // 사용자 정보 업데이트
            userMapper.updateUserInfo(params.getNo(), params.getProfile(), params.getName());

            // 사용자 관심 카테고리를 초기화 한 후 다시 셋팅
            userMapper.deleteUserCategory(params.getNo());
            for (int i = 0; i < params.getCategoryList().size(); i++) {
                userMapper.insertUserCategory(params.getCategoryList().get(i), params.getNo());
            }

            // 수정된 사용자 정보를 토대로 JWT 토큰 재 생성
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

    public void changeUserPwdByEmail(String pwd, String email) {
        authMapper.changeUserPwdByEmail(pwd, email);
    }

    public void deleteUserByNo(int userNo) {
        authMapper.deleteUserByNo(userNo);
    }

    public void updateUserProfileByNo(MultipartFile profileFile, String prevProfile, int userNo, String name) throws IOException {
        String profileUrl = "";
        if(profileFile == null) {
            profileUrl = prevProfile;
        } else {
            s3Uploader.delete(prevProfile);
            profileUrl = s3Uploader.upload(profileFile, "user/" + name + "/profile");
        }

        userMapper.updateUserInfo(userNo, profileUrl, name);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
