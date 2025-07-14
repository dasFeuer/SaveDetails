package com.example.CollectionProject.services.impl;


import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.UpdateCredentialsRequest;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.exceptions.CredentialsException;
import com.example.CollectionProject.repositories.SaveCredentialsRepository;
import com.example.CollectionProject.services.SaveCredentialsService;
import com.example.CollectionProject.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        throw new CredentialsException("Unknown error occurred");
    }

    @Override
    @Transactional
    public SaveCredentials updateTheUserCredentials(Long id, UpdateCredentialsRequest updateCredentialsRequest) {
        Optional<SaveCredentials> credentials = saveCredentialsRepository.findById(id);
        if(credentials.isPresent()) {
            SaveCredentials updateCredentials = credentials.get();
            updateCredentials.setHeader(updateCredentialsRequest.getHeader());
            updateCredentials.setEmail(updateCredentialsRequest.getEmail());
            updateCredentials.setUsername(updateCredentialsRequest.getUsername());
            updateCredentials.setPassword(updateCredentialsRequest.getPassword());
            updateCredentials.setRemarks(updateCredentialsRequest.getRemarks());
            log.info("Updated from Service");
            return saveCredentialsRepository.save(updateCredentials);
        }
        throw new RuntimeException();
    }

    @Override
    public void deleteCredentialsById(Long id) {
        saveCredentialsRepository.deleteById(id);
    }

    @Override
    public Optional<SaveCredentials> getCredentialsById(Long credentialId) {
        return saveCredentialsRepository.findById(credentialId);
    }

    @Override
    public List<SaveCredentials> getAllCredentials() {
        return saveCredentialsRepository.findAll();
    }
}
