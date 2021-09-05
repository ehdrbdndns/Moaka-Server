package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Bookmark;
import com.moaka.dto.Like;
import com.moaka.service.BookmarkService;
import com.moaka.service.LikeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "청크 좋아요 생성", notes = "청크 좋아요 생성")
    @PostMapping(value = "/user/insertLikeOfChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertBookmarkOfChunk(@ApiParam(value = "chunk_no와 JWT TOKEN 필요", required = true)
                                              @RequestBody Like params,
                                              @RequestHeader Map<String, String> headers) {
        try {
            JSONObject result = new JSONObject();
            params.setUser_no(jwtTokenProvider.getUserNo(headers.get("bearer")));
            result.put("like_no", likeService.insertLikeOfChunk(params));
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "좋아요 삭제", notes = "좋아요 삭제")
    @PostMapping(value = "/user/deleteLike", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBookmark(@ApiParam(value = "like 고유번호 필요", required = true)
                                              @RequestBody Like params) {
        try {
            JSONObject result = new JSONObject();
            likeService.deleteLike(params.getNo());
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
