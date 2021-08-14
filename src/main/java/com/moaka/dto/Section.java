package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Section {
    private int no;
    private String title;
    private String archive_no;
    private String regdate;
}
