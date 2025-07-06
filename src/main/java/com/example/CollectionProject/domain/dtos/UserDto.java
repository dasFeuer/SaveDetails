package com.example.CollectionProject.domain.dtos;

import com.example.CollectionProject.domain.dtosSummary.SaveCredentialsSummaryDto;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private List<SaveCredentialsSummaryDto> credentialsList = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
