package com.example.CollectionProject.services.impl;


import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.repositories.SaveCredentialsRepository;
import com.example.CollectionProject.services.SaveCredentialsService;
import com.example.CollectionProject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveCredentialsServiceImpl implements SaveCredentialsService {

    private final SaveCredentialsRepository saveCredentialsRepository;
    private final UserService userService;

    @Override
    public SaveCredentials createTheUserCredentials(
            CreateCredentialsRequest createCredentialsRequest,
            String email)
    {
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()){
            SaveCredentials saveCredentials = new SaveCredentials();
            saveCredentials.setHeader(createCredentialsRequest.getHeader());
            saveCredentials.setEmail(createCredentialsRequest.getEmail());
            saveCredentials.setUsername(createCredentialsRequest.getUsername());
            saveCredentials.setPassword(createCredentialsRequest.getPassword());
            saveCredentials.setRemarks(createCredentialsRequest.getRemarks());
            saveCredentials.setOwnerOfCredentials(user.get());

            return saveCredentialsRepository.save(saveCredentials);
        }
        throw new RuntimeException();
    }
}
