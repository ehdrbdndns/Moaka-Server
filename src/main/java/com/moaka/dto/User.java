package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private int no;
    @ApiParam(value = "사용자 고유 번호", required = false, example = "sub 값")
    private String sub;
    @ApiParam(value = "아이디", required = false, example = "ID 값")
    private String id;
    @ApiParam(value = "비밀번호", required = false, example = "PWD 값")
    private String pwd;
    @ApiParam(value = "사용자 이름", required = false, example = "홍길동")
    private String name;
    @ApiParam(value = "사용자 나이", required = false, example = "21")
    private String age;
    @ApiParam(value = "사용자 프로파일 이미지", required = false, example = "https://profile.com/img.png")
    private String profile;
    @ApiParam(value = "사용자 Auth 타입", required = false, example = "google or local")
    private String auth_type;
    // 사용자 생성 날짜
    private String regdate;
}
