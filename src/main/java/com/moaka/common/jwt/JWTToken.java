package com.moaka.common.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class JWTToken {
    // User Dto와 동일
    private int no;
    private String sub;
    private String id;
    private String pwd;
    private String name;
    private int age;
    private String profile;
    private String auth_type;
    // 사용자 생성 날짜
    private String regdate;

    // JWT 버전
    private String version;

    public JWTToken() {
    }
}
