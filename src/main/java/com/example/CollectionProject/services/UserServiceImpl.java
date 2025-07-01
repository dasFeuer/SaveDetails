package com.example.CollectionProject.services;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.dtos.RegisterUserRequest;
import com.example.CollectionProject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(RegisterUserRequest registerUserRequest) {
        User newUser = new User();
        newUser.setName(registerUserRequest.getName());
        newUser.setEmail(registerUserRequest.getEmail());
        newUser.setUsername(registerUserRequest.getUsername());
        newUser.setPassword(registerUserRequest.getPassword());
        return userRepository.save(newUser);
    }
}
