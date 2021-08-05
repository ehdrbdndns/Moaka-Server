package com.moaka.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INDEX_NOT_FOUND(1001, "인덱스가 존재하지 않습니다."),
    USER_NOT_FOUND(1002, "회원 정보가 존재하지 않습니다.");

    private int errorCode;
    private String errorMessage;

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
