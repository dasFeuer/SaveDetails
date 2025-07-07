package com.example.CollectionProject.mappers;

import com.example.CollectionProject.domain.CreateCredentialsRequest;
import com.example.CollectionProject.domain.UpdateCredentialsRequest;
import com.example.CollectionProject.domain.dtos.CreateCredentialsRequestDto;
import com.example.CollectionProject.domain.dtos.UpdateCredentialsRequestDto;
import com.example.CollectionProject.domain.dtosSummary.UpdateCredentialsSummaryDto;
import com.example.CollectionProject.domain.entities.SaveCredentials;
import com.example.CollectionProject.domain.dtos.SaveCredentialsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SaveCredentialsMapper {

    @Mapping(target = "ownerOfCredentials", source = "ownerOfCredentials")
    SaveCredentialsDto toSaveCredentials(SaveCredentials saveCredentials);

    CreateCredentialsRequest toCreateCredentials(CreateCredentialsRequestDto createCredentialsRequestDto);

    UpdateCredentialsRequest toUpdateCredentials(UpdateCredentialsRequestDto updateCredentialsRequestDto);

    UpdateCredentialsSummaryDto toUpdateCredentialsSummary(SaveCredentials saveCredentials);
}
