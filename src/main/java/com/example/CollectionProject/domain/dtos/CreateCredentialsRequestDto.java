package com.example.CollectionProject.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCredentialsRequestDto {

    @NotBlank(message = "Header is required")
    private String header;

    @NotBlank(message = "Email is required")
    private String email;

    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String remarks;

}
