package com.example.board_spring2.dto;

import com.example.board_spring2.entity.Board;
import com.example.board_spring2.entity.Timestamped;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
