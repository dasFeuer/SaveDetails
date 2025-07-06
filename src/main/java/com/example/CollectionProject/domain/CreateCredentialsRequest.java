package com.example.CollectionProject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCredentialsRequest {
    private String header;
    private String email;
    private String username;
    private String password;
    private String remarks;
}
