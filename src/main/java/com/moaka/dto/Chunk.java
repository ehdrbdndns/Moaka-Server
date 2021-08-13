package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Chunk {
    private int no;
    @ApiParam(value = "URL", required = false, example = "https://moaka.com")
    private String link;
    @ApiParam(value = "URL 제목", required = false)
    private String link_title;
    @ApiParam(value = "URL 내용", required = false)
    private String link_description;
    @ApiParam(value = "제목", required = false)
    private String title;
    @ApiParam(value = "내용", required = false)
    private String description;
    @ApiParam(value = "URL 썸네일 cdn 링크", required = false, example = "https://moaka.com/good.png")
    private String thumbnail;
    @ApiParam(value = "청크를 담고있는 섹션 번호", required = false)
    private int section_no;
    private String regdate;
}
