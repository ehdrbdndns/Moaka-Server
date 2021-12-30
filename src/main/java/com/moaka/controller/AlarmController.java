package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Alarm;
import com.moaka.service.AlarmService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class AlarmController {
    @Autowired
    AlarmService alarmService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "알람 리스트 가져오기기", notes = "")
    @PostMapping(value = "/user/retrieveAlarmByUserNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveAlarmByUserNo(@RequestHeader Map<String, String> headers) {
        try {
            JSONObject result = new JSONObject();
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            ArrayList<Alarm> alarmList = alarmService.retrieveAlarmByUserNo(user_no);
            result.put("alarm_list", alarmList);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
