package com.moaka.springserver.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResult {
    private int code;
    private HttpStatus status;
    private String message;
}