package com.example.CollectionProject.services;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.domain.dtos.RegisterUserRequest;
import com.example.CollectionProject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
