package com.example.CollectionProject.repositories;

import com.example.CollectionProject.domain.entities.SaveCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveCredentialsRepository extends JpaRepository<SaveCredentials, Long> {

}
