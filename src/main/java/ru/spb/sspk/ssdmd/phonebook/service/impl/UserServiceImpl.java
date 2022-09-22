package ru.spb.sspk.ssdmd.phonebook.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.spb.sspk.ssdmd.phonebook.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook.model.mapper.UserMapper;
import ru.spb.sspk.ssdmd.phonebook.repository.UserRepository;
import ru.spb.sspk.ssdmd.phonebook.service.UserService;

import java.util.Optional;

@Component
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(Long userId, String userFirstName, String userLastName) {

        UserDto userDto = new UserDto.Builder()
                .setUserId(userId)
                .setUserFirstName(userFirstName)
                .setUserLastName(userLastName)
                .build();

        userMapper.toDto(Optional.ofNullable(userRepository.save(userMapper.toEntity(userDto))));

        return null;
    }

    @Override
    public Long findAll(Long userId) {
        UserDto userDto = null;

        userDto = userMapper.toDto(userRepository.findByUserId(userId));

        if (userDto.getUserId() == userId) {
            return userId;
        } else {
            return null;
        }
    }
}
