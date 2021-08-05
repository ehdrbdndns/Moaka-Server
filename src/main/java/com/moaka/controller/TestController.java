package com.moaka.controller;

import com.moaka.common.exception.NotFoundException;
import com.moaka.dto.User;
import com.moaka.service.TestService;
import com.moaka.common.exception.ErrorCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TestController {
    @Autowired
    public TestService testService;

    @ApiOperation(value = "사용자 정보 조회", notes = "UserId를 이용하여 사용자 정보를 조회합니다.")
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findUser(
            @ApiParam(value = "user id", required = true, example = "1")
            @PathVariable(value = "id", required = true) String id,
            @ApiParam(value = "User Agent Type ", required = true, example = "Mozila")
            @RequestHeader(value = "User-Agent") String userAgent,
            @ApiParam(value = "parameter1 ", required = false)
            @RequestParam(value = "param1", required = false) String param1,
            @ApiParam(value = "parameter2 ", required = false)
            @RequestParam(value = "param2", required = false) String param2){

        return true;
    }

    @ApiOperation(value = "사용자 리스트 조회", notes = "특정 조건에 맞는 사용자 리스트를 조회합니다.")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findUsers(
            @RequestHeader(value = "User-Agent") String userAgent,
            @ModelAttribute User user){
        testService.getUserCount();
        ArrayList<User> userList = testService.getUser();
        System.out.println(userList);
        return true;
    }

    @ApiOperation(value = "사용자 생성", notes = "신규 사용자를 생성합니다.")
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object CreateUser(
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody(required = true) User user){

        return true;
    }

    @GetMapping("/nfe")
    public ResponseEntity notFound() {
        throw new NotFoundException(ErrorCode.INDEX_NOT_FOUND.getErrorCode(), ErrorCode.INDEX_NOT_FOUND.getErrorMessage());
    }
}
