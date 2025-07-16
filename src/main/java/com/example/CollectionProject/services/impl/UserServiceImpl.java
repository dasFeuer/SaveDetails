package com.example.CollectionProject.services.impl;

import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.exceptions.UserNotFoundException;
import com.example.CollectionProject.exceptions.UserUpdateException;
import com.example.CollectionProject.repositories.UserRepository;
import com.example.CollectionProject.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            newUser
                    .setPassword
                    (passwordEncoder
                            .encode(registerUserRequest
                                    .getPassword()));
            return userRepository.save(newUser);
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        return userRepository.findByEmail(email);
    }


    @Override
    public User updateUser(Long id, UpdateUserRequest updateUserRequest) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User newUserInfo = existingUser.get();
            newUserInfo.setName(updateUserRequest.getName());
            newUserInfo.setUsername(updateUserRequest.getUsername());
            newUserInfo.setEmail(updateUserRequest.getEmail());
            newUserInfo
                    .setPassword(passwordEncoder
                    .encode(updateUserRequest
                            .getPassword()));
            return userRepository.save(newUserInfo);
        }
        throw new UserUpdateException("User ID cannot be null");
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must not be empty");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

}
