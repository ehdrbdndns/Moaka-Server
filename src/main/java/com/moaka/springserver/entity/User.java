package com.moaka.springserver.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class User {
    @ApiParam(value = "사용자 이름", required = false, example = "홍길동")
    private String userName;
    @ApiParam(value = "휴대폰 번호", required = false, example = "010-0000-0000")
    private String phoneNumber;
}
