package com.moaka.controller;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.exception.NotFoundException;
import com.moaka.dto.Bookmark;
import com.moaka.dto.User;
import com.moaka.service.ChatService;
import com.moaka.service.TestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "채팅 정보 가져오기", notes = "채팅 정보 가져오기")
    @PostMapping(value = "/retrieveChatByRoomNo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveChatByRoomNo( @ApiParam(value = "아카이브 번호", required = true)
                                                            @RequestParam("roomNo") int roomNo,
                                                        @RequestHeader Map<String, String> headers) {
        try {
            JSONObject result = new JSONObject();
            int user_no = 0;
            if (jwtTokenProvider.validateToken(headers.get("bearer"))) {
                user_no = jwtTokenProvider.getUserNo(headers.get("bearer"));
            }
            result.put("chat_list", chatService.retrieveChatByRoomNo(roomNo, user_no));
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
