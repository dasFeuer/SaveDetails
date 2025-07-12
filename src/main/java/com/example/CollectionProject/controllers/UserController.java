package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.AuthResponse;
import com.example.CollectionProject.domain.LoginUserRequest;
import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.dtos.UpdateUserRequestDto;
import com.example.CollectionProject.domain.dtosSummary.UserSummaryDto;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.domain.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.domain.dtos.UserDto;
import com.example.CollectionProject.mappers.UserMapper;
import com.example.CollectionProject.services.UserService;
import com.example.CollectionProject.services.impl.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private final AuthenticationManager authentication;
    private final JwtService jwtService;

    private Optional<User> getAuthenticatedUser(){
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        String email = authenticated.getName();
        return userService.getUserByEmail(email);
    }

    private User getAuthenticatedUserOrThrow() {
        return getAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));
    }

    private void validateUserAccess(Long userId) {
        User user = getAuthenticatedUserOrThrow();
        if (!user.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }


    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        RegisterUserRequest registerUserRequest =  userMapper.toRegister(registerUserRequestDto);
        User newUser = userService.createUser(registerUserRequest);
        UserDto user = userMapper.toUser(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<AuthResponse> loginUser(
            @Valid @RequestBody LoginUserRequest loginUserRequest) {
        Authentication authenticated = authentication.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequest.getEmail(),
                loginUserRequest.getPassword()
        ));

        String token = jwtService.generateToken(authenticated.getName());
        AuthResponse loginResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400L)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PutMapping("/{id}/updateUser")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto
            ) {
        validateUserAccess(id);
        UpdateUserRequest update = userMapper.toUpdate(updateUserRequestDto);
        User user = userService.updateUser(id, update);
        UserDto updatedUser = userMapper.toUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserSummaryDto>> getAllUsers() {
        getAuthenticatedUserOrThrow();
        List<User> allUser = userService.getAllUser();
        List<UserSummaryDto> collectAllUsers = allUser
                .stream()
                .map(userMapper::toSummary)
                .toList();

        return ResponseEntity.ok(collectAllUsers);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail
            (
            @RequestParam("email") String email) {
        getAuthenticatedUserOrThrow();
        Optional<User> userByEmail = userService.getUserByEmail(email);
        return userByEmail
                .map(user -> ResponseEntity.ok(userMapper.toUser(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping("/emailDomain")
    public ResponseEntity<Set<UserDto>> getUserByEmailDomain
            (
            @RequestParam("emailDomain") String domain) {
        getAuthenticatedUserOrThrow();
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
        validateUserAccess(id);
        userService.deleteUserById(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user-withGmail")
    public ResponseEntity<List<UserDto>> getUserByEmailDomain() {
        getAuthenticatedUserOrThrow();
        List<User> allUser = userService.getAllUser();
        List<UserDto> collectAllUsers = allUser
                .stream()
                .filter(user -> user.getEmail().endsWith("@gmail.com"))
                .map(userMapper::toUser)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(collectAllUsers);
    }
}
