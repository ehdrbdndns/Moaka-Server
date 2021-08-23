package com.moaka.controller;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Section;
import com.moaka.service.SectionService;
import com.moaka.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class SectionController {
    @Autowired
    SectionService sectionService;

    @ApiOperation(value = "섹션 생성", notes = "사용자의 섹션을 생성합니다.")
    @PostMapping(value = "/user/insertSection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertSection(@RequestBody Section params)
    {
        try{
            System.out.println(params);

            sectionService.insertSection(params);

            JSONObject result = new JSONObject();
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "섹션 삭제", notes = "사용자의 섹션을 삭제합니다.")
    @PostMapping(value = "/user/deleteSection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteSection(
            @ApiParam(value = "섹션 번호", required = true)
            @RequestBody Section params)
    {
        try{
            sectionService.deleteSection(params.getNo());
            JSONObject result = new JSONObject();
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
