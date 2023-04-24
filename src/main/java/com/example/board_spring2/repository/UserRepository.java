package com.example.board_spring2.repository;

import com.example.board_spring2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);

}
