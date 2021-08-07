package com.moaka.controller;

import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.User;
import com.moaka.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    EncryptionService encryptionService;

    @ApiOperation(value = "로그인", notes = "구글 사용자는 sub, 로컬 사용자는 ID와 PWD로 로그인을 합니다.")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@ApiParam(value = "sub", required = false, example = "123456789")
                        @RequestParam(value = "sub", required = false) String sub,
                                            @ApiParam(value = "id", required = false)
                        @RequestParam(value = "id", required = false) String id,
                                            @ApiParam(value = "pwd", required = false)
                        @RequestParam(value = "pwd", required = false) String pwd,
                                            @ApiParam(value = "auth_type ", required = true, example = "google")
                        @RequestParam(value = "auth_type", required = true) String auth_type) throws NoSuchAlgorithmException {
            User user = new User();
            user.setAuth_type(auth_type);
            if(auth_type.equals("google")) {
                user.setSub(sub);
            } else {
                user.setId(id);
                user.setPwd(encryptionService.encryptionSHA256(pwd));
                System.out.println(user.getPwd());
            }
            JSONObject result = authService.login(user);
        return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
    }
}
