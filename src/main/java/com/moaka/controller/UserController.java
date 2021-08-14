package com.moaka.controller;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Archive;
import com.moaka.dto.User;
import com.moaka.service.AuthService;
import com.moaka.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "개인 디렉토리 찾기", notes = "사용자의 개인 아카이브를 검색합니다.")
    @PostMapping(value = "/retrieveLocalDirectory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveLocalDirectory(@ApiParam(value = "user_no", required = true, example = "4")
                                                         @RequestParam(value = "user_no") int user_no) {
        try{
            JSONArray directoryList = userService.retrieveDirectory(user_no);
            return new ResponseEntity<>(directoryList.toString(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
