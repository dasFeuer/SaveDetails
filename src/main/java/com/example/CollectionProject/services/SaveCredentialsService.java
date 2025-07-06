package com.example.CollectionProject.services;

import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.entities.SaveCredentials;

public interface SaveCredentialsService {
    SaveCredentials createTheUserCredentials(CreateCredentialsRequest createCredentialsRequest, String email);
}
