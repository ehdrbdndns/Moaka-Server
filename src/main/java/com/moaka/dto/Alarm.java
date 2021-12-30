package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Alarm {
    private int no;
    private int user_no;
    private String content;
    private String send_name;
    private String send_profile;
    private String regdate;
}
