package ru.spb.sspk.ssdmd.phonebook_test.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;
import ru.spb.sspk.ssdmd.phonebook_test.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User save(User user) {
        jdbcTemplate.update(
                "insert into user_bot (" +
                        "user_id) " +
                        "values (?)",
                user.getUserId());

        return user;
    }

    @Override
    public List<User> findByUserId(Long userId) {
        List<User> users = jdbcTemplate.query(
                "select user_id " +
                        "from user_bot where user_id = ?",
                this::mapRowToUser, userId);
        return users;
    }

    @Override
    public void delete(Long id) {

        jdbcTemplate.update("delete from user_bot where user_id = ?");

    }

    private User mapRowToUser(ResultSet row, int rowNum) throws SQLException {

        return new User();
    }
}
