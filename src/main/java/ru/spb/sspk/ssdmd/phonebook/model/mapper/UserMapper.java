package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.mapstruct.Mapper;
import ru.spb.sspk.ssdmd.phonebook.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.User;

import java.util.Optional;

@Mapper
public interface UserMapper {

    UserDto toDto(Optional<User> user);

    User toEntity(UserDto userDto);

}
