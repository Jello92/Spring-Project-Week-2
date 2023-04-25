package com.example.board_spring2.service;

import com.example.board_spring2.dto.LoginRequestDto;
import com.example.board_spring2.dto.ResponseDto;
import com.example.board_spring2.dto.SignupRequestDto;
import com.example.board_spring2.entity.User;
import com.example.board_spring2.jwt.JwtUtil;
import com.example.board_spring2.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto signUp(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        if(username.length() < 4 || username.length() > 10 || !Pattern.matches("[a-z0-9]*$", username)){
            return new ResponseDto("아이디를 다시 입력해 주세요!", 100);
        }
        if(password.length() < 8 || password.length() > 15 || !Pattern.matches("[A-Za-z0-9]*$", password)){
            return new ResponseDto("비밀번호를 다시 입력해 주세요!",100);
        }

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()){
            return new ResponseDto("같은 아이디가 이미 있습니다!", 100);
        }

        User user = new User(username, password);
        userRepository.save(user);

        ResponseDto responseDto = new ResponseDto("회원가입 성공", 200);
        return new ResponseDto("회원가입 성공", 200);
    }

    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return new ResponseDto("로그인 성공", 200);
    }
}
