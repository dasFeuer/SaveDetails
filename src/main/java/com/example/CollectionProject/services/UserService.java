package com.example.CollectionProject.services;


import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.dtos.RegisterUserRequest;

public interface UserService {
    User createUser(RegisterUserRequest registerUserRequest);
}
