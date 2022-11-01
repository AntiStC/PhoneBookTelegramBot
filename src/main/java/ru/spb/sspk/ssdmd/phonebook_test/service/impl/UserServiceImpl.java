package ru.spb.sspk.ssdmd.phonebook_test.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.UserMapper;
import ru.spb.sspk.ssdmd.phonebook_test.repository.UserRepository;
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(Long userId) {

        UserDto userDto = new UserDto();

        UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDto)));

        return null;
    }

    @Override
    public String findAll(Long userId) {

        List<UserDto> list = new ArrayList<>();
        for (User user : userRepository.findByUserId(userId)) {
            UserDto userDto = UserMapper.INSTANCE.toDto(user);
            list.add(userDto);
        }

            return list.toString().replace("[", "")
                    .replace("]","");

    }
}
