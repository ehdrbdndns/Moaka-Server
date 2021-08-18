package com.moaka.controller;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import com.moaka.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class BookmarkController {
    @Autowired
    BookmarkService bookmarkService;

    @ApiOperation(value = "북마크 삽입", notes = "북마크를 사용자 개인 저장소에 저장합니다.")
    @PostMapping(value = "/insertBookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertBookmark(@ApiParam(value = "user_no", example = "3")
                                                 @RequestParam(value = "user_no") int user_no,
                                                 @ApiParam(value = "link", example = "https://moaka.com")
                                                 @RequestParam(value = "link") String link,
                                                 @ApiParam(value = "link_title")
                                                 @RequestParam(value = "link_title") String link_title,
                                                 @ApiParam(value = "link_description")
                                                 @RequestParam(value = "link_description") String link_description,
                                                 @ApiParam(value = "title", example = "제목")
                                                 @RequestParam(value = "title") String title,
                                                 @ApiParam(value = "description", example = "내용")
                                                 @RequestParam(value = "description") String description,
                                                 @ApiParam(value = "thumbnail", example = "썸네일")
                                                 @RequestParam(value = "thumbnail") String thumbnail,
                                                 @ApiParam(value = "section_no")
                                                 @RequestParam(value = "section_no") int section_no) {
        try {
            System.out.println(user_no);
            Chunk chunk = new Chunk();
            chunk.setLink(link);
            chunk.setLink_title(link_title);
            chunk.setLink_description(link_description);
            chunk.setTitle(title);
            chunk.setDescription(description);
            chunk.setThumbnail(thumbnail);
            chunk.setSection_no(section_no);
            bookmarkService.insertBookmarkToChunk(chunk, user_no);
            JSONObject result = new JSONObject();
            result.put("isSuccess", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.INTERNAL_SERVICE.getErrorCode(), ErrorCode.INTERNAL_SERVICE.getErrorMessage());
        }
    }
}
