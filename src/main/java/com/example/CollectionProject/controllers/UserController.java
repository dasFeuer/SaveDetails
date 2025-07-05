package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.dtos.UpdateUserRequestDto;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PutMapping("/{id}/updateUser")
    public ResponseEntity<UserDto> registerUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto
            ) {
        UpdateUserRequest update = userMapper.toUpdate(updateUserRequestDto);
        User user = userService.updateUser(id, update);
        UserDto updatedUser = userMapper.toUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
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

    @PostMapping("/email")
    public ResponseEntity<Optional<User>> getUserByEmail
            (
            @RequestParam("email") String email) {
        Optional<User> userByEmail = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userByEmail);
    }

    @GetMapping("/emailDomain")
    public ResponseEntity<Set<UserDto>> getUserByEmailDomain
            (
            @RequestParam("emailDomain") String domain) {
        List<User> allUser = userService.getAllUser();
        Set<UserDto> collect = allUser
                .stream()
                .filter(user -> user.getEmail().toLowerCase()
                        .endsWith(domain.toLowerCase()))
                .map(userMapper::toUser)
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(collect);
    }

    @DeleteMapping("/{id}/user")
    public ResponseEntity<Void> deleteUserById (@PathVariable Long id) {
        userService.deleteUserById(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user-withGmail")
    public ResponseEntity<List<UserDto>> getUserByEmailDomain() {
        List<User> allUser = userService.getAllUser();
        List<UserDto> collectAllUsers = allUser
                .stream()
                .filter(user -> user.getEmail().endsWith("@gmail.com"))
                .map(userMapper::toUser)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(collectAllUsers);
    }
}
