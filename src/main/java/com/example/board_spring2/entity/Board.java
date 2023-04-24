package com.example.board_spring2.entity;

import com.example.board_spring2.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long userid;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String username;

    public Board(BoardRequestDto boardRequestDto, Long id, String username){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.username = boardRequestDto.getUsername();
    }

    public void update(BoardRequestDto boardRequestDto){
        this.content = boardRequestDto.getContent();
        this.title = boardRequestDto.getTitle();
        this.username = boardRequestDto.getUsername();
    }

}
