package com.moaka.service;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Archive;
import com.moaka.dto.User;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.SectionMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    AuthMapper authMapper;
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    SectionMapper sectionMapper;
    
    @Autowired
    EncryptionService encryptionService;

    public JSONObject login(User params) {
        try{
            JSONObject result = new JSONObject();
            User userInfo = authMapper.login(params);
            String message = "";
            boolean isLogin = false;
            if(userInfo != null) {
                System.out.println("로그인 성공");
                isLogin = true;
                message = encryptionService.encryptionJWT(userInfo);
            } else {
                System.out.println("로그인 실패");
                isLogin = false;
                message = "he is not member";
            }
            result.put("isLogin", isLogin);
            result.put("token", message);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    public JSONObject register(User params) throws Exception {
        JSONObject result = new JSONObject();
        authMapper.register(params);
        result.put("no", params.getNo());
        result.put("isSuccess", true);
        return result;
    }
}
