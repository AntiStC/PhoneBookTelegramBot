package ru.spb.sspk.ssdmd.phonebook_test.repository;

import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    List<User> findByUserId(Long userId);

    void delete(Long id);
}
