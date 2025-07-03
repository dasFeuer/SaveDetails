package com.example.CollectionProject.services;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.domain.dtos.RegisterUserRequest;
import com.example.CollectionProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User createUser(RegisterUserRequest registerUserRequest) {
        if (registerUserRequest.getUsername() == null ||
                registerUserRequest.getEmail() == null) {
            throw new IllegalArgumentException("Email or Username cannot be null");
        }
        boolean userExistsByEmailOrUsername = userRepository
                .existsByEmail
                        (registerUserRequest.getEmail())
                || userRepository
                .existsByUsername
                        (registerUserRequest.getUsername());
        if (userExistsByEmailOrUsername) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is same username or email already exists!");
        } else {
            User newUser = new User();
            newUser.setName(registerUserRequest.getName());
            newUser.setEmail(registerUserRequest.getEmail());
            newUser.setUsername(registerUserRequest.getUsername());
            newUser.setPassword(registerUserRequest.getPassword());
            return userRepository.save(newUser);
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
