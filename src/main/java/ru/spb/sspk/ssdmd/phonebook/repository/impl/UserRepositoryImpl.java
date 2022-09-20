package ru.spb.sspk.ssdmd.phonebook.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook.model.entity.User;
import ru.spb.sspk.ssdmd.phonebook.repository.UserRepository;

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
                        "id, " +
                        "user_id, " +
                        "user_first_name, " +
                        "user_last_name) " +
                        "values (?, ?, ?, ?)",
                user.getId(),
                user.getUserId(),
                user.getUserFirstName(),
                user.getUserLastName()
        );

        return user;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        List<User> users = jdbcTemplate.query(
                "select id, " +
                        "user_id, " +
                        "user_first_name, " +
                        "user_last_name " +
                        "from user_bot where user_id = ?",
                this::mapRowToUser, userId
        );
        return users.size() == 0 ?
                Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void delete(Long id) {

        jdbcTemplate.update("delete from user_bot where user_id = ?");

    }

    private User mapRowToUser(ResultSet row, int rowNum) throws SQLException {

        return new User.Builder()
                .setId(row.getLong(1))
                .setUserId(row.getLong(2))
                .setUserFirstName(row.getString(3))
                .setUserLastName(row.getString(4))
                .build();
    }
}
