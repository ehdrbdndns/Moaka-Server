package com.moaka.controller;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Mail;
import com.moaka.service.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @ApiOperation(value = "회원가입 인증 메일 전송", notes = "회원가입 인증 메일 전송")
    @PostMapping(value = "/insertMailCodeOfRegister", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertMailCodeOfRegister(@RequestParam(value = "address", required = true) String address) {
        try {
            Mail mail = new Mail();
            mail.setAddress(address);
            JSONObject result = mailService.mailSendOfRegister(mail);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "인증 코드 메일 전송", notes = "인증 메일 전송")
    @PostMapping(value = "/sendMailCode",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMailCode(@RequestParam(value = "address", required = true) String address) {
        try {
            Mail mail = new Mail();
            mail.setAddress(address);
            System.out.println("sendMailCode");
            JSONObject result = mailService.mailSend(mail, "모아카 비밀번호 찾기 인증번호입니다.");
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "메일 인증 확인", notes = "메일 인증 확인")
    @PostMapping(value = "/isExistMailCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> isExistMailCode(@RequestParam(value = "no") int no, @RequestParam(value = "code") int code) {
        try {
            JSONObject result = mailService.isExistCode(no, code);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "메일 인증 기한 만료", notes = "메일 인증 확인")
    @PostMapping(value = "/expireMailCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> expireMailCode(@RequestParam(value = "no") int no) {
        try {
            JSONObject result = mailService.expireMailCode(no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
