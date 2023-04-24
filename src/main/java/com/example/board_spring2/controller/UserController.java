package com.example.board_spring2.controller;

import com.example.board_spring2.dto.LoginRequestDto;
import com.example.board_spring2.dto.ResponseDto;
import com.example.board_spring2.dto.SignupRequestDto;
import com.example.board_spring2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signUp(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto login (@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}
