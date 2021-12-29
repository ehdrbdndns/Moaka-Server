package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Chat {
    private int no;
    private String content;
    private int room_no;
    private int user_no;
    private String regdate;
}
