package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.spb.sspk.ssdmd.phonebook.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}