package com.moaka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
public class MailCode {
    private int no;
    private int code;
    private int valid;
    private String regdate;
}
