package com.example.CollectionProject.services;

import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<User> getUserByEmail(String email) {
        if(!email.isEmpty()){
            return userRepository.findByEmail(email);
        }
        throw new RuntimeException();
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest updateUserRequest) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User newUserInfo = existingUser.get();
            newUserInfo.setName(updateUserRequest.getName());
            newUserInfo.setUsername(updateUserRequest.getUsername());
            newUserInfo.setEmail(updateUserRequest.getEmail());
            newUserInfo.setPassword(updateUserRequest.getPassword());
            return userRepository.save(newUserInfo);
        }
        throw new RuntimeException();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);

    }

}
