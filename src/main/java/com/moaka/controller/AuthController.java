package com.moaka.controller;

import com.moaka.common.cdn.S3Uploader;
import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    S3Uploader s3Uploader;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

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
        if (auth_type.equals("google")) {
            user.setSub(sub);
        } else {
            user.setId(id);
            user.setPwd(encryptionService.encryptionSHA256(pwd));
        }
        JSONObject result = authService.login(user);
        return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "구글 회원가입", notes = "구글 사용자 회원가입")
    @PostMapping(value = "/googleRegister",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> googleRegister(@ApiParam(value="sub")
                                                @RequestParam(value="sub") String sub,
                                                @ApiParam(value = "id")
                                                @RequestParam(value = "id") String id,
                                                @ApiParam(value = "name")
                                                @RequestParam(value = "name") String name,
                                                @ApiParam(value = "profileFile")
                                                @RequestParam(value = "profileFile", required = false) MultipartFile profileFile,
                                                @ApiParam(value = "profile")
                                                @RequestParam(value="profile", required = false) String profile,
                                                @ApiParam(value="categoryList")
                                                @RequestParam(value="categoryList") ArrayList<String> categoryList) {
        try {
            User params = new User();
            params.setSub(sub);
            params.setId(id);
            params.setPwd(" ");
            params.setName(name);
            params.setCategoryList(categoryList);
            params.setAuth_type("google");
            params.setRegdate(getToday());
            params.setAge(0);

            if(profileFile != null) {
                String url = s3Uploader.upload(profileFile, "user/" + name + "/profile");
                params.setProfile(url);
            } else {
                params.setProfile(profile);
            }

            JSONObject result = authService.register(params);

            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "로컬 회원가입", notes = "로컬 사용자 회원가입")
    @PostMapping(value = "/localRegister",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> localRegister(@ApiParam(value = "id")
                                                @RequestParam(value = "id") String id,
                                                @ApiParam(value = "pwd")
                                                @RequestParam(value = "pwd") String pwd,
                                                @ApiParam(value = "name")
                                                @RequestParam(value = "name") String name,
                                                @ApiParam(value = "profileFile")
                                                @RequestParam(value = "profileFile", required = false) MultipartFile profileFile,
                                                @ApiParam(value="categoryList")
                                                @RequestParam(value="categoryList") ArrayList<String> categoryList) {
        try {
            User params = new User();
            params.setId(id);
            params.setPwd(encryptionService.encryptionSHA256(pwd));
            params.setName(name);
            params.setCategoryList(categoryList);
            params.setSub("local_" + id);
            params.setAuth_type("local");
            params.setRegdate(getToday());
            params.setAge(0);

            if(profileFile != null) {
                String url = s3Uploader.upload(profileFile, "user/" + name + "/profile");
                params.setProfile(url);
            } else {
                params.setProfile("/img/default_profile.png");
            }

            JSONObject result = authService.register(params);

            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "회원가입", notes = "구글 사용자는 sub와 사용자 정보, 로컬 사용자는 ID PWD와 사용자 정보로 회원가입을 합니다.")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@ApiParam(value = "sub", required = false)
                                           @RequestParam(value = "sub", required = false, defaultValue = "") String sub,
                                           @ApiParam(value = "id", required = true, example = "test@test.com")
                                           @RequestParam(value = "id", required = true) String id,
                                           @ApiParam(value = "pwd", required = false)
                                           @RequestParam(value = "pwd", required = false, defaultValue = "") String pwd,
                                           @ApiParam(value = "name", required = true)
                                           @RequestParam(value = "name", required = true) String name,
                                           @ApiParam(value = "profile", required = false)
                                           @RequestParam(value = "profile", required = false, defaultValue = "./img/moaka_logo.png") String profile,
                                           @ApiParam(value = "auth_type ", required = true, example = "google")
                                           @RequestParam(value = "auth_type", required = true) String auth_type) {
        try {
            User params = new User();
            if (sub.equals("")) {
                sub = "local_" + id;
            }
            params.setSub(sub);
            params.setId(id);
            if (pwd.equals("")) {
                params.setPwd(pwd);
            } else {
                pwd = encryptionService.encryptionSHA256(pwd);
                params.setPwd(pwd);
            }
            params.setName(name);
            params.setProfile(profile);
            params.setAuth_type(auth_type);
            params.setAge(0);
            params.setRegdate(getToday());

            JSONObject result = authService.register(params);

            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "JWT 토큰으로 사용자 정보 가져오기", notes = "JWT 토큰으로 사용자 정보 가져오기")
    @PostMapping(value = "/user/setUserFromToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setUser(@RequestHeader Map<String, String> headers) {
        try {
            JSONObject result = jwtTokenProvider.setUser(headers.get("bearer"));
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "사용자 정보 업데이트", notes = "사용자의 정보를 업데이트합니다.")
    @PostMapping(value = "/user/updateUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserInfo(@RequestPart(value = "user") User params,
                                                 @RequestPart(value = "profileFile", required = false) MultipartFile profileFile,
                                                 @RequestHeader Map<String, String> headers) {
        try {
            params.setNo(jwtTokenProvider.getUserNo(headers.get("bearer")));
            if (profileFile != null) {
                params.setProfileFile(profileFile);
            }
            JSONObject result = authService.updateUserInfo(params);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "사용자 비밀번호 변경", notes = "사용자의 비밀번호를 변경합니다.")
    @PostMapping(value = "/changeUserPwd", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeUserPwd(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "pwd") String pwd) {
        try {
            String _pwd = encryptionService.encryptionSHA256(pwd);
            authService.changeUserPwdByEmail(_pwd, email);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
