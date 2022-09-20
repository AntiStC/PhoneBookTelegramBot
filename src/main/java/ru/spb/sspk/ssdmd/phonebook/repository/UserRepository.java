package ru.spb.sspk.ssdmd.phonebook.repository;

import ru.spb.sspk.ssdmd.phonebook.model.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUserId(Long userId);

    void delete(Long id);
}
