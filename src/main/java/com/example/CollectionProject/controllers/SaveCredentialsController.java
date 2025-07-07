package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.domain.UpdateCredentialsRequest;
import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.dtos.*;
import com.example.CollectionProject.domain.dtosSummary.UpdateCredentialsSummaryDto;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.mappers.SaveCredentialsMapper;
import com.example.CollectionProject.mappers.UserMapper;
import com.example.CollectionProject.services.SaveCredentialsService;
import com.example.CollectionProject.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/credentials")
public class SaveCredentialsController {

    private final SaveCredentialsService credentialsService;
    private final SaveCredentialsMapper credentialsMapper;

    @PostMapping("/saveCredentials")
    public ResponseEntity<SaveCredentialsDto> saveCredentials(
            @Valid @RequestBody CreateCredentialsRequestDto createCredentialsRequestDto,
            @RequestParam("email") String email) {
        CreateCredentialsRequest createCredentials = credentialsMapper.toCreateCredentials(createCredentialsRequestDto);
        SaveCredentials theUserCredentials = credentialsService.createTheUserCredentials(createCredentials, email);
        SaveCredentialsDto saveCredentials = credentialsMapper.toSaveCredentials(theUserCredentials);
        return ResponseEntity.status(HttpStatus.OK).body(saveCredentials);
    }

    @PutMapping("/{id}/updateCredentials")
    public ResponseEntity<UpdateCredentialsSummaryDto> updateCredentials(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCredentialsRequestDto updateCredentialsRequestDto
            ) {
        UpdateCredentialsRequest updateCredentials = credentialsMapper.toUpdateCredentials(updateCredentialsRequestDto);
        SaveCredentials saveCredentials = credentialsService.updateTheUserCredentials(id, updateCredentials);
        UpdateCredentialsSummaryDto updatedCredentials = credentialsMapper.toUpdateCredentialsSummary(saveCredentials);
        log.info("Updated from Controller");
        return ResponseEntity.status(HttpStatus.OK).body(updatedCredentials);
    }
}
