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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SectionController {
    @Autowired
    SectionService sectionService;

    @ApiOperation(value = "섹션 생성", notes = "사용자의 섹션을 생성합니다.")
    @PostMapping(value = "/user/insertSection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertSection(@ApiParam(value = "archive_no", required = true)
                                                         @RequestParam(value = "archive_no") int archive_no,
                                                @ApiParam(value = "title", required = true)
                                                @RequestParam(value = "title") String title,
                                                @ApiParam(value = "description", required = true)
                                                    @RequestParam(value = "description") String description,
                                                @ApiParam(value = "tag_list", required = true)
                                                    @RequestParam(value = "tag_list") ArrayList<String> tag_list)
    {
        try{
            Section params = new Section();
            params.setArchive_no(archive_no);
            params.setTitle(title);
            params.setDescription(description);
            params.setTag_list(tag_list);

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
}
