package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Chunk;
import com.moaka.dto.Comment;
import com.moaka.service.ChunkService;
import com.moaka.service.CommentService;
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
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "청크 댓글 생성", notes = "청크 댓글 생성")
    @PostMapping(value = "/user/insertCommentOfChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertCommentOfChunk(@ApiParam(value = "댓글에 들어가는 전반적인 내용", required = true)
                                                       @RequestBody Comment params,
                                                       @RequestHeader Map<String, String> headers) {
        try {
            params.setUser_no(jwtTokenProvider.getUserNo(headers.get("bearer")));
            params.setProfile(jwtTokenProvider.getUserProfile(headers.get("bearer")));
            params.setName(jwtTokenProvider.getUserName(headers.get("bearer")));
            JSONObject result = commentService.insertCommentOfChunk(params);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "청크 댓글 삭제", notes = "청크 댓글 삭제")
    @PostMapping(value = "/user/deleteCommentOfChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCommentOfChunk(@ApiParam(value = "댓글 고유번호", required = true)
                                                       @RequestBody Comment params,
                                                       @RequestHeader Map<String, String> headers) {
        try {
            JSONObject result = commentService.deleteCommentOfChunk(jwtTokenProvider.getUserNo(headers.get("bearer")), params.getNo());
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
