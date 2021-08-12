package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Archive {
    private int no;
    private String title;
    private String thumbnail;
    private String privacy_type;
    private int user_no;
    private String regdate;

    ArrayList<Section> sectionList;
}
