package com.example.board_spring2.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;

    private String content;

    private String username;

    private String password;
}
