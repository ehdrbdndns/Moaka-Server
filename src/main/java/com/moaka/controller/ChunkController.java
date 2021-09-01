package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Chunk;
import com.moaka.service.BookmarkService;
import com.moaka.service.ChunkService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ChunkController {
    @Autowired
    ChunkService chunkService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "링크 생성", notes = "링크 생성")
    @PostMapping(value = "/user/insertChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertChunk(@ApiParam(value = "청크에 들어가는 전반적인 내용", required = true)
                                              @RequestBody Chunk params) {
        try {
            chunkService.insertChunk(params);
            JSONObject result = new JSONObject();
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "링크 수정", notes = "링크 수정")
    @PostMapping(value = "/user/updateChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateChunk(@ApiParam(value = "청크에 들어가는 전반적인 내용", required = true)
                                              @RequestBody Chunk params) {
        try {
            chunkService.updateChunk(params);
            JSONObject result = new JSONObject();
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }

    @ApiOperation(value = "링크 삭제", notes = "링크 삭제")
    @PostMapping(value = "/user/deleteChunk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteChunk(@ApiParam(value = "청크 고유 번호 및 JWT 토큰", required = true)
                                              @RequestBody Chunk params,
                                              @RequestHeader Map<String, String> headers) {
        try {
            params.setUser_no(jwtTokenProvider.getUserNo(headers.get("bearer")));
            boolean isSuccess = chunkService.deleteChunk(params);
            JSONObject result = new JSONObject();
            result.put("isSuccess", isSuccess);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
