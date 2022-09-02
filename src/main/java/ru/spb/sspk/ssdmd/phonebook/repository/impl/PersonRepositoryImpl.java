package ru.spb.sspk.ssdmd.phonebook.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook.repository.PersonRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public List<Person> findByDepartment(String department) {

        return jdbcTemplate.query(
                "select id, last_name, middle_name, last_name, department, phone " +
                        "from person where department = ?",
                this::mapRowToPerson, department);
    }

    @Override
    public List<Person> findByFirstName(String firstname) {

        return jdbcTemplate.query(
                "select id, last_name, middle_name, last_name, department, phone " +
                        "from person where first_name = ?",
                this::mapRowToPerson, firstname);
    }

    @Override
    public List<Person> findByLastName(String lastname) {

        return jdbcTemplate.query(
                "select " +
                        "id, " +
                        "last_name, " +
                        "middle_name, " +
                        "last_name, " +
                        "department, " +
                        "phone " +
                        "from person where last_name = ?",
                this::mapRowToPerson, lastname);
    }

    @Override
    public Optional<Person> findByPhone(Integer phone) {

        List<Person> results = jdbcTemplate.query(
                "select " +
                        "id, " +
                        "last_name, " +
                        "middle_name, " +
                        "last_name, " +
                        "department, " +
                        "phone " +
                        "from person where phone = ?",
                this::mapRowToPerson, phone);

        return results.size() == 0 ?
                Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void deleteById(Long id) {

        jdbcTemplate.update("delete from person where id = ?");
    }

    private Person mapRowToPerson(ResultSet row, int rowNum) throws SQLException {

        return new Person.Builder().
                setId(row.getLong(1)).
                setFirstName(row.getString(2)).
                setMiddleName(row.getString(3)).
                setLastName(row.getString(4)).
                setDepartment(row.getString(5)).
                setPhone(row.getInt(6)).
                build();
    }
}
