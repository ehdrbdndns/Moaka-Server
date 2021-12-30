package com.moaka.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
@ToString
public class Archive {
    private int no;
    private String title;
    private String description;
    private String category;
    private String thumbnail;
    private MultipartFile thumbnailFile;
    private String privacy_type;
    private String regdate;
    // 생성자 이름
    private String creator_name;
    private String creator_profile;
    // 생성자 고유번호
    private int user_no;

    // 링크 개수
    private int link_count;

    // 좋아요 개수
    private int like_count;

    // 북마크 개수
    private int bookmark_count;

    private int bookmark_no;
    private int like_no;

    // 아카이브 형태 eX) 검색, 그룹, 추천, 북마크 등등
    private String type;

    ArrayList<User> user_list;
    ArrayList<Section> section_list;
    ArrayList<String> tag_list;
    ArrayList<Integer> group_no_list;
}
