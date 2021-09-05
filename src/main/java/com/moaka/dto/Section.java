package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Section {
    @ApiParam(value = "섹션 고유 번호", required = false)
    private int no;
    @ApiParam(value = "섹션 제목", required = false)
    private String title;
    @ApiParam(value = "섹션 내용", required = false)
    private String description;
    @ApiParam(value = "아카이브 번호", required = false)
    private int archive_no;
    @ApiParam(value = "섹션 생성 날짜", required = false)
    private String regdate;

    @ApiParam(value = "섹션 태그 리스트", required = false)
    private ArrayList<String> tag_list;
}
