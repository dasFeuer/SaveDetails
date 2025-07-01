package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.dtos.RegisterUserRequest;
import com.example.CollectionProject.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.dtos.UserDto;
import com.example.CollectionProject.mappers.UserMapper;
import com.example.CollectionProject.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        RegisterUserRequest registerUserRequest =  userMapper.toRegister(registerUserRequestDto);
        User newUser = userService.createUser(registerUserRequest);
        UserDto user = userMapper.toUser(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
