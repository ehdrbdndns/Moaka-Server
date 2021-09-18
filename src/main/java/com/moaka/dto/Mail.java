package com.moaka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mail {
    private int no;
    private String address;
    private String title;
    private String message;
}
