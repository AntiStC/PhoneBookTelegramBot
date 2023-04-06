package ru.spb.sspk.ssdmd.phonebook_test.service.impl;

import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.UserMapper;
import ru.spb.sspk.ssdmd.phonebook_test.repository.UserRepository;
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(Long userId) {

        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        return userMapper.INSTANCE.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public Long findAll(Long userId) {

        UserDto userDto = userMapper.INSTANCE.toDto(userRepository.findByUserId(userId));
        if (userDto == null) {
            return null;
        } else {
            return userDto.getUserId();
        }
    }
}