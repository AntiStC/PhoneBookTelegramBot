package ru.spb.sspk.ssdmd.phonebook.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook.repository.PersonRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private JdbcTemplate jdbcTemplate;

    public PersonRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person save(Person person) {
        jdbcTemplate.update(
                "insert into person (" +
                        "id, " +
                        "first_name, " +
                        "middle_name, " +
                        "last_name, " +
                        "department, " +
                        "phone) " +
                        "values (?, ?, ?, ?, ?, ?)",
                person.getId(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getDepartment(),
                person.getPhone());

        return person;
    }

    @Override
    public Person update(Person person) {

        jdbcTemplate.update("update person " +
                        "set first_name = (?), " +
                        "middle_name = (?), " +
                        "last_name = (?), " +
                        "department = (?), " +
                        "phone = (?) " +
                        "where id = (?) " +
                        "returning id",
                person.getId(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getDepartment(),
                person.getPhone());

        return person;
    }

    @Override
    public List<Person> findAll() {

        return jdbcTemplate.query(
                "select id, first_name, middle_name, last_name, department, phone " +
                        "from person",
                this::mapRowToPerson);
    }

    @Override
    public List<Person> findByAll(String answer) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        Map<String,Object> paramMap = new HashMap<>(16);
        paramMap.put("answer", answer);

        return namedParameterJdbcTemplate.query("select id, last_name, middle_name, last_name, department, phone " +
                "from person where first_name = :answer " +
                "or last_name = :answer " +
                "or department = :answer"
                , paramMap, this::mapRowToPerson);

    }


    @Override
    public void deleteById(Long id) {

        jdbcTemplate.update("delete from person where id = ?");
    }

    private Person mapRowToPerson(ResultSet row, int rowNum) throws SQLException {

        return new Person.Builder().
                setId(row.getLong("id")).
                setFirstName(row.getString("first_name")).
                setMiddleName(row.getString("middle_name")).
                setLastName(row.getString("last_name")).
                setDepartment(row.getString("department")).
                setPhone(row.getInt("phone")).
                build();
    }
}
