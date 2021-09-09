package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Like {
    @ApiParam(value = "섹션 고유 번호", required = false)
    private int no;
    @ApiParam(value = "사용자 고유번호", required = false)
    private int user_no;
    @ApiParam(value = "청크 고유번호", required = false)
    private int chunk_no;
    @ApiParam(value = "아카이브 고유 번호", required = false)
    private int archive_no;
    @ApiParam(value = "생성 날짜", required = false)
    private String regdate;


}
