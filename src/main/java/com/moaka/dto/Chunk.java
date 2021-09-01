package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Chunk {
    @ApiParam(value = "청크 고유번호", required = false, example = "https://moaka.com")
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
    @ApiParam(value = "청크 만든 사람의 유저 번호", required = false)
    private int user_no;
    @ApiParam(value = "청크 계층", required = false)
    private int layer;
    @ApiParam(value = "관련 청크 순서", required = false)
    private int content_order;
    @ApiParam(value = "관련 청크 그룹", required = false)
    private int group_num;
    @ApiParam(value = "생성 날짜", required = false)
    private String regdate;

    // 청크의 댓글 리스트
    private ArrayList<String> tagList;
    private ArrayList<Comment> commentList;
    private ArrayList<Chunk> chunkList;
}
