package ru.spb.sspk.ssdmd.phonebook_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findByUserId(Long userId);

}
