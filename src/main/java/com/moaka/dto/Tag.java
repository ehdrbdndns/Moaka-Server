package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Tag {
    @ApiParam(value = "태그 고유 번호", required = false)
    private int no;
    @ApiParam(value = "태그 내용", required = false)
    private String tag;
    @ApiParam(value = "태그가 적용될 아카이브 번호", required = false)
    private int archive_no;
    @ApiParam(value = "태그가 적용될 섹션 번호", required = false)
    private int section_no;
    @ApiParam(value = "태그가 적용될 청크 내용", required = false)
    private int chunk_no;
    @ApiParam(value = "태그가 적용된 저장소(아카이브, 섹션, 청크)", required = false)
    private String store_type;
    @ApiParam(value = "태그 생성 날짜", required = false)
    private String regdate;
}
