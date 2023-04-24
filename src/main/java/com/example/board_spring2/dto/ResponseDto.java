package com.example.board_spring2.dto;

import lombok.Data;

@Data
public class ResponseDto {

    private String message;
    private Object data;

    public ResponseDto(String message, Object data){
        this.message = message;
        this.data = data;
    }
}
