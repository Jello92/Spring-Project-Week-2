package com.example.board_spring2.service;

import com.example.board_spring2.dto.BoardRequestDto;
import com.example.board_spring2.dto.BoardResponseDto;
import com.example.board_spring2.dto.ResponseDto;
import com.example.board_spring2.entity.Board;
import com.example.board_spring2.entity.User;
import com.example.board_spring2.jwt.JwtUtil;
import com.example.board_spring2.repository.BoardRepository;
import com.example.board_spring2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("Token not found");
        }
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        User user = checkUser(claims);
        Board board = new Board(boardRequestDto, user.getId(), user.getUsername());
        Long id = boardRepository.saveAndFlush(board).getId();
        return new BoardResponseDto(checkBoard(id));
    }

    public BoardResponseDto getBoard(Long id) {
        return new BoardResponseDto(checkBoard(id));
    }

    public List<BoardResponseDto> getBoardList() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = checkUser(claims);
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("포스트가 없습니다.")
            );

            if (board.getUsername().equals(user.getUsername())) {
                board.update(boardRequestDto);
            }
            return new BoardResponseDto(checkBoard(id));
        } else {
            return null;
        }
    }

    @Transactional
    public ResponseDto delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = checkUser(claims);
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("포스트가 없습니다.")
            );

            if (board.getUsername().equals(user.getUsername()))
                boardRepository.delete(board);
            return new ResponseDto("게시글 삭제 성공", 200);
        } else {
            throw new IllegalArgumentException("삭제할 권한이 없습니다.");
        }
    }

    private Board checkBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 없습니다.")
        );
    }

    private User checkUser(Claims claims) {
        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }
}
