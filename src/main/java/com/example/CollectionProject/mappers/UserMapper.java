package com.example.CollectionProject.mappers;

import com.example.CollectionProject.domain.UpdateUserRequest;
import com.example.CollectionProject.domain.dtos.UpdateUserRequestDto;
import com.example.CollectionProject.domain.dtosSummary.UserSummaryDto;
import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.RegisterUserRequest;
import com.example.CollectionProject.domain.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.domain.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUser(User user);
    UserSummaryDto toSummary(User user);
    RegisterUserRequest toRegister(RegisterUserRequestDto registerUserRequestDto);
    UpdateUserRequest toUpdate(UpdateUserRequestDto updateUserRequestDto);
}
