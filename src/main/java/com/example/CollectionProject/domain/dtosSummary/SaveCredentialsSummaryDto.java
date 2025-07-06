package com.example.CollectionProject.domain.dtosSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCredentialsSummaryDto {
    private Long id;
    private String header;
    private String email;
    private String username;
    private String password;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
