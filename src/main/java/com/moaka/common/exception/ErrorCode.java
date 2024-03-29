package com.moaka.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INDEX_NOT_FOUND(1001, "인덱스가 존재하지 않습니다."),
    USER_NOT_FOUND(1002, "회원 정보가 존재하지 않습니다."),
    JWT_NOT_FOUND(1003, "JWT 토큰이 생성되지 않습니다."),
    INTERNAL_SERVICE(1004, "서버에 문제가 발생했습니다."),
    AWS_CDN_NOT_WORK(1005, "AWS CDN 서버가 정상적으로 작동되지 않습니다."),
    FILE_NOT_CHANGE(1006, "파일이 정삭적으로 변환되지 않습니다.");

    private int errorCode;
    private String errorMessage;

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
