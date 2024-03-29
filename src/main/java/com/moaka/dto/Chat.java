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

    private String user_name;
    private String user_profile;

    private int like_count;
    private int like_no;

    // websocket type
    private String type; // like, message
}
