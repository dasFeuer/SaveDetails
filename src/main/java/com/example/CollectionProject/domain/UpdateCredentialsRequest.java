package com.example.CollectionProject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCredentialsRequest {
    private String header;
    private String email;
    private String username;
    private String password;
    private String remarks;
}
