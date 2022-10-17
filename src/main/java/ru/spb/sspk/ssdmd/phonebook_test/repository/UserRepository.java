package ru.spb.sspk.ssdmd.phonebook_test.repository;

import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUserId(Long userId);

    void delete(Long id);
}
