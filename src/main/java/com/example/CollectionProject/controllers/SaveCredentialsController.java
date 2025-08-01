package com.example.CollectionProject.controllers;

import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.domain.UpdateCredentialsRequest;
import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.dtos.*;
import com.example.CollectionProject.domain.dtosSummary.SaveCredentialsSummaryDto;
import com.example.CollectionProject.domain.dtosSummary.UpdateCredentialsSummaryDto;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.exceptions.CredentialsNotFoundException;
import com.example.CollectionProject.mappers.SaveCredentialsMapper;
import com.example.CollectionProject.mappers.UserMapper;
import com.example.CollectionProject.services.SaveCredentialsService;
import com.example.CollectionProject.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/credentials")
public class SaveCredentialsController {

    private final SaveCredentialsService credentialsService;
    private final SaveCredentialsMapper credentialsMapper;
    private final UserService userService;
    private final AuthenticationManager authentication;


    public Optional<User> getAuthenticatedUser(){
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        String email = authenticated.getName();
        return userService.getUserByEmail(email);
    }

    public User getAuthenticatedUserOrThrow(){
        return getAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));
    }

    private void validateUserCredentialsAccess(Long credentialId) {
        User user = getAuthenticatedUserOrThrow();
        SaveCredentials credentials = credentialsService.getCredentialsById(credentialId)
                .orElseThrow(CredentialsNotFoundException::new);
        if (!credentials.getOwnerOfCredentials().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }
    @PostMapping("/saveCredentials")
    public ResponseEntity<SaveCredentialsDto> saveCredentials(
            @Valid @RequestBody CreateCredentialsRequestDto createCredentialsRequestDto,
            @RequestParam("email") String email) {
        getAuthenticatedUserOrThrow();
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
        validateUserCredentialsAccess(id);
        UpdateCredentialsRequest updateCredentials = credentialsMapper.toUpdateCredentials(updateCredentialsRequestDto);
        SaveCredentials saveCredentials = credentialsService.updateTheUserCredentials(id, updateCredentials);
        UpdateCredentialsSummaryDto updatedCredentials = credentialsMapper.toUpdateCredentialsSummary(saveCredentials);
        log.info("Updated from Controller");
        return ResponseEntity.status(HttpStatus.OK).body(updatedCredentials);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCredentials(@PathVariable Long id) {
        validateUserCredentialsAccess(id);
        credentialsService.deleteCredentialsById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all-Credentials")
    public ResponseEntity<List<SaveCredentialsSummaryDto>> getAllCredentials() {
        getAuthenticatedUserOrThrow();
        List<SaveCredentials> allCredentials = credentialsService.getAllCredentials();
        List<SaveCredentialsSummaryDto> collect = allCredentials
                .stream()
                .map(credentialsMapper::toSaveCredentialsSummaryDto)
                .toList();

        return ResponseEntity.ok(collect);
    }
    
    @GetMapping("/{id}/credentials")
    public ResponseEntity<SaveCredentialsSummaryDto> getById(@PathVariable Long id) {
        validateUserCredentialsAccess(id);
        Optional<SaveCredentials> credentialsById = credentialsService.getCredentialsById(id);
        if(credentialsById.isPresent()){
            SaveCredentialsSummaryDto saveCredentialsSummaryDto = credentialsMapper
                    .toSaveCredentialsSummaryDto(credentialsById.get());
            return ResponseEntity.ok(saveCredentialsSummaryDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
