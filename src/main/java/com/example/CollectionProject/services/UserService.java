package com.example.CollectionProject.services;


import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.domain.dtos.RegisterUserRequest;

import java.util.List;

public interface UserService {

    User createUser(RegisterUserRequest registerUserRequest);
    List<User> getAllUser();

}
