package com.example.CollectionProject.domain.dtos;

import com.example.CollectionProject.domain.dtosSummary.UserSummaryDto;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCredentialsDto {
    private Long id;
    private UserSummaryDto ownerOfCredentials;
    private String header;
    private String email;
    private String username;
    private String password;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
