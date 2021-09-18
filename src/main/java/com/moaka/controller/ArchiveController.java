package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Archive;
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
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation(value = "아카이브의 디테일 페이지", notes = "해당 아카이브 정보를 가져옵니다.")
    @PostMapping(value = "/retrieveArchiveFromArchiveNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveFromArchiveNo(
            @ApiParam(value = "아카이브 번호", required = true)
            @RequestParam("archive_no") int archive_no,
            @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveFromArchiveNo(archive_no, user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브 삭제", notes = "아카이브 고유 번호와 JWT 토큰으로 아카이브를 삭제합니다.")
    @PostMapping(value = "/user/deleteArchiveFromArchiveNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteArchiveFromArchiveNo(
            @ApiParam(value = "아카이브 번호", required = true)
            @RequestParam("archive_no") int archive_no,
            @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.deleteArchiveFromArchiveNo(archive_no, user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브 생성", notes = "아카이브를 생성합니다.")
    @PostMapping(value = "/user/insertArchive", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> insertArchive(
            @RequestPart(value = "thumbnailFile") MultipartFile thumbnailFile,
            @RequestPart(value="archive") Archive params,
            @RequestHeader Map<String, String> headers) {
        try {
            params.setThumbnailFile(thumbnailFile);
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            params.setUser_no(user_no);
            archiveService.insertArchive(params);
            JSONObject result = archiveService.retrieveArchiveFromArchiveNo(params.getNo(), user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}