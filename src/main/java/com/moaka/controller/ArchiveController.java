package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Archive;
import com.moaka.dto.Section;
import com.moaka.service.ArchiveService;
import com.moaka.service.SectionService;
import com.moaka.service.UserService;
import io.swagger.annotations.Api;
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

import java.util.List;
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
    @PostMapping(value = "/user/retrieveArchiveOfGroupByUserNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveOfGroupByUserNo(@ApiParam(value = "JWT Token만 필요", required = false)
                                                                 @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveOfGroupByUserNo(user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "북마크한 아카이브 리스트 검색", notes = "사용자가 북마크한 아카이브를 검색합니다.")
    @PostMapping(value = "/user/retrieveArchiveOfBookmarkByUserNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveOfBookmarkByUserNo(@RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveOfBookmarkByUserNo(user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "상위 아카이브 리스트 검색", notes = "상위 아카이브를 검색합니다.")
    @PostMapping(value = "/retrieveArchiveOfTop", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveOfTop() {
        try {
            JSONObject result = archiveService.retrieveArchiveOfTop();
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

    @ApiOperation(value = "아카이브의 관심 카테고리 리스트", notes = "아카이브의 관심 카테고리 리스트")
    @PostMapping(value = "/user/retrieveArchiveOfCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveOfCategory(@ApiParam(value = "카테고리 리스트", required = true)
                                                            @RequestHeader Map<String, String> headers) {
        try {
            List<String> categoryList = jwtTokenProvider.getCategoryList(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveOfCategory(categoryList);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브 생성", notes = "아카이브를 생성합니다.")
    @PostMapping(value = "/user/insertArchive", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> insertArchive(
            @RequestPart(value = "thumbnailFile") MultipartFile thumbnailFile,
            @RequestPart(value = "archive") Archive params,
            @RequestHeader Map<String, String> headers) {
        try {
            params.setThumbnailFile(thumbnailFile);
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            params.setUser_no(user_no);
            archiveService.insertArchive(params);
            JSONObject result = archiveService.retrieveArchiveFromArchiveNo(params.getNo(), user_no);
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브 수정", notes = "아카이브를 수정합니다.")
    @PostMapping(value = "/user/updateArchive", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateArchive(@ApiParam(value = "수정될 아카이브 내용")
                                                @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
                                                @RequestPart(value = "archive") Archive params) {
        try {
            if (thumbnailFile != null) {
                params.setThumbnailFile(thumbnailFile);
            }
            JSONObject result = archiveService.updateArchive(params);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아키이브 검색", notes = "아카이브를 검색합니다.")
    @PostMapping(value = "/retrieveArchiveBySearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveArchiveBySearch(@ApiParam(value = "검색 내용 내용", required = true)
                                                          @RequestPart(value = "p") String param,
                                                          @RequestHeader Map<String, String> headers) {
        try {
            int user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            JSONObject result = archiveService.retrieveArchiveBySearch(param, user_no);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
