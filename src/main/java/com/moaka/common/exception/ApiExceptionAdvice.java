package com.moaka.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> notFoundException(NotFoundException e) {
        ErrorResult er = new ErrorResult();
        er.setCode(e.getCode());
        er.setStatus(HttpStatus.NOT_FOUND);
        er.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(InternalServiceException.class)
    public ResponseEntity<ErrorResult> serviceException(InternalServiceException e) {
        ErrorResult er = new ErrorResult();
        er.setCode(e.getCode());
        er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        er.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
    }
}
