package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Comment {
    @ApiParam(value = "댓글 고유번호", required = false)
    private int no;
    @ApiParam(value = "청크(게시글) 고유번호", required = false)
    private int chunk_no;
    @ApiParam(value = "사용자 고유번호", required = false)
    private int user_no;
    @ApiParam(value = "댓글 내용", required = false)
    private String content;
    @ApiParam(value = "댓글 순서", required = false)
    private int content_order;
    @ApiParam(value = "댓글 계층", required = false)
    private int layer;
    @ApiParam(value = "댓글 그룹", required = false)
    private int group_num;
    @ApiParam(value = "생성 날짜", required = false)
    private String regdate;

    // 댓글 단 유저 프로필
    private String profile;
    // 댓글 단 유저 이름
    private String name;

}
