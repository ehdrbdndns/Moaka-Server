package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Archive {
    private int no;
    private String title;
    private String description;
    private String thumbnail;
    private String privacy_type;
    // 생성자 이름
    private String creator_name;
    // 생성자 고유번호
    private int user_no;
    private String regdate;
    
    ArrayList<Section> section_list;
    ArrayList<String> tag_list;
}
