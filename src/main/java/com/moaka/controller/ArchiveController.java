package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Section;
import com.moaka.service.ArchiveService;
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

import java.util.Map;

@RestController
public class ArchiveController {
    @Autowired
    ArchiveService archiveService;
    @Autowired
    SectionService sectionService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "소속 아카이브 리스트 검색", notes = "사용자의 소속 아카이브를 검색합니다.")
    @PostMapping(value = "/user/retrieveArchiveFromGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveFromGroup(@ApiParam(value = "JWT Token만 필요", required = false)
                                                           @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveFromGroup(user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브의 디테일 페이지", notes = "사용자의 섹션 리스트를 불러옵니다.")
    @PostMapping(value = "/archive/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getArchiveDetail(
            @ApiParam(value = "아카이브 번호및 JWT TOKEN", required = true)
            @RequestParam("no") int archive_no,
            @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONArray sectionList = sectionService.retrieveSectionByArchiveNo(archive_no, user_no);
            return new ResponseEntity<>(sectionList.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
