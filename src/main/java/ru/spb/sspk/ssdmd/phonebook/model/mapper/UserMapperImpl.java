package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.User;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto toDto = new UserDto();
        toDto.setUserId(user.getUserId());
        return toDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User toEntity = new User();
        toEntity.setUserId(userDto.getUserId());
        return toEntity;
    }
}
