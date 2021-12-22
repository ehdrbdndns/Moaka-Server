package com.moaka.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class Bookmark {
    private int no;
    private int user_no;
    private int archive_no;
    private String regdate;
}
