package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.dtos.RegisterUserRequest;
import com.example.CollectionProject.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.dtos.RegisterUserResponseDto;
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
    public ResponseEntity<RegisterUserResponseDto> registerUser(
            @Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        RegisterUserRequest registerUserRequest =  userMapper.toRegister(registerUserRequestDto);
        User newUser = userService.createUser(registerUserRequest);
        UserDto user = userMapper.toUser(newUser);
        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        registerUserResponseDto.setId(user.getId());
        registerUserResponseDto.setName(user.getName());
        registerUserResponseDto.setEmail(user.getEmail());
        registerUserResponseDto.setUsername(user.getUsername());
        registerUserResponseDto.setPassword(user.getPassword());
        registerUserResponseDto.setCreatedAt(user.getCreatedAt());
        registerUserResponseDto.setUpdatedAt(user.getUpdatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(registerUserResponseDto);
    }
}
