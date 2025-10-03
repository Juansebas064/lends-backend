package com.app.lends_backend.mapper;

import com.app.lends_backend.dto.UserDto;
import com.app.lends_backend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
