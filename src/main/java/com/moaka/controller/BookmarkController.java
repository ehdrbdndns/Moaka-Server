package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Bookmark;
import com.moaka.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BookmarkController {
    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "청크 북마크 생성", notes = "청크 북마크 생성")
    @PostMapping(value = "/user/insertBookmarkOfChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertBookmarkOfChunk(@ApiParam(value = "bookmark의 chunk_no가 필요", required = true)
                                              @RequestBody Bookmark params,
                                              @RequestHeader Map<String, String> headers) {
        try {
            params.setUser_no(jwtTokenProvider.getUserNo(headers.get("bearer")));
            JSONObject result = new JSONObject();
            result.put("bookmark_no", bookmarkService.insertBookmarkOfChunk(params));
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "아카이브 북마크 생성", notes = "아카이브 북마크 생성")
    @PostMapping(value = "/user/insertBookmarkOfArchive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertBookmarkOfArchive(@ApiParam(value = "bookmark의 archive_no가 필요", required = true)
                                                        @RequestBody Bookmark params,
                                                        @RequestHeader Map<String, String> headers) {
        try {
            params.setUser_no(jwtTokenProvider.getUserNo(headers.get("bearer")));
            JSONObject result = new JSONObject();
            result.put("bookmark_no", bookmarkService.insertBookmarkOfArchive(params));
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "링크 북마크 삭제", notes = "링크 북마크 삭제")
    @PostMapping(value = "/user/deleteBookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBookmark(@ApiParam(value = "bookmark의 bookmark_no가 필요", required = true)
                                              @RequestBody Bookmark params) {
        try {
            JSONObject result = new JSONObject();
            bookmarkService.deleteBookmarkOfChunk(params);
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
