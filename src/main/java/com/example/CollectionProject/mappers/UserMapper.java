package com.example.CollectionProject.mappers;

import com.example.CollectionProject.domain.User;
import com.example.CollectionProject.dtos.RegisterUserRequest;
import com.example.CollectionProject.dtos.RegisterUserRequestDto;
import com.example.CollectionProject.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUser(User user);
    RegisterUserRequest toRegister(RegisterUserRequestDto registerUserRequestDto);
}
