package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.CreateUserCommand;
import com.wasilewskyy.medical_clinic.model.User;
import com.wasilewskyy.medical_clinic.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toUser(CreateUserCommand command);
}
