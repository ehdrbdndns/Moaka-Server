package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.config.security.user.CustomUserDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class JwtAuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @ApiOperation(value = "JWT 토큰 받기 인증 테스트", notes = "북마크를 사용자 개인 저장소에 저장합니다.")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@ApiParam(value = "id", example = "ehdrbdndns@naver.com")
                                                @RequestParam(value = "id") String id) throws Exception {
        return jwtTokenProvider.createToken(id, Collections.singletonList("ROLE_USER"), 1, "testName", "./img/");
    }

    @ApiOperation(value = "JWT 토큰 인증 테스트", notes = "북마크를 사용자 개인 저장소에 저장합니다.")
    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST)
    public String checkAuthenticationToken() throws Exception {
        return "hi";
    }
}
