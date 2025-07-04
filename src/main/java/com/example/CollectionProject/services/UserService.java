package com.example.CollectionProject.services;


import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(RegisterUserRequest registerUserRequest);
    List<User> getAllUser();
    Optional<User> getUserByEmail(String email);
    User updateUser(Long id, UpdateUserRequest updateUserRequest);
}
