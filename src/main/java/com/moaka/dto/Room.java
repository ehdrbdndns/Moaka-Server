package com.moaka.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Room {
    private int no;
    private String room_id;
    private int chunk_no;
    private String regdate;
}
