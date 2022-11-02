package ru.spb.sspk.ssdmd.phonebook_test.service;

import org.springframework.stereotype.Service;
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto;

@Service
public interface UserService {

    UserDto save(Long userId);

    Long findAll(Long userId);
}
