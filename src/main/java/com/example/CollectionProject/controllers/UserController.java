package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.domain.dtos.RegisterUserRequest;
import com.example.CollectionProject.domain.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.domain.dtos.UserDto;
import com.example.CollectionProject.mappers.UserMapper;
import com.example.CollectionProject.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> allUser = userService.getAllUser();
        List<UserDto> collectAllUsers = allUser
                .stream()
                .map(userMapper::toUser)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(collectAllUsers);
    }
}
