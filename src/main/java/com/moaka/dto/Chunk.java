package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Chunk {
    // TODO 신버전
    @ApiParam(value = "청크 고유번호", required = false, example = "https://moaka.com")
    private int no;
    @ApiParam(value = "URL", required = false, example = "https://moaka.com")
    private String link;
    @ApiParam(value = "URL 썸네일 cdn 링크", required = false, example = "https://moaka.com/good.png")
    private String thumbnail;
    @ApiParam(value = "청크를 담고있는 섹션 번호", required = false)
    private int section_no;
    @ApiParam(value = "내용", required = false)
    private String description;
    @ApiParam(value = "생성 날짜", required = false)
    private String regdate;
    @ApiParam(value = "청크 만든 사람의 유저 번호", required = false)
    private int user_no;
    @ApiParam(value = "Favicon", required = false)
    private String favicon;
    @ApiParam(value = "link 도메인")
    private String domain;
    @ApiParam(value = "채팅방 번호")
    private String room_id;
    @ApiParam(value = "채팅방 고유 번호")
    private int room_no;
    @ApiParam(value = "채팅 개수")
    private String chat_count;

    // TODO 구버전
    @ApiParam(value = "URL 제목", required = false)
    private String link_title;
    @ApiParam(value = "URL 내용", required = false)
    private String link_description;
    @ApiParam(value = "제목", required = false)
    private String title;
    @ApiParam(value = "청크 계층", required = false)
    private int layer;
    @ApiParam(value = "관련 청크 순서", required = false)
    private int content_order;
    @ApiParam(value = "관련 청크 그룹", required = false)
    private int group_num;

    // 청크의 관련 청크 리스트
    private ArrayList<Chunk> relative_chunk_list;
    private boolean relative_chunk_loading = false;

    // 청크의 태그 리스트
    private ArrayList<String> tag_list;

    // 청크 북마크
    private int bookmark_no;
    private boolean bookmark_loading = false;

    // 청크 좋아요
    private int like_no;
    private int like_count;
    private boolean chunk_loading = false;

    private ArrayList<Chunk> chunk_list;
}
