package ru.spb.sspk.ssdmd.phonebook_test.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook_test.repository.PersonRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
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
                person.getPhone(),
                person.getMobilPhone());

        return person;
    }

    @Override
    public Person update(Person person) {

        jdbcTemplate.update("update person " +
                        "set first_name = (?), " +
                        "middle_name = (?), " +
                        "last_name = (?), " +
                        "department = (?), " +
                        "phone = (?), " +
                        "mobil_phone = (?) " +
                        "where id = (?) " +
                        "returning id",
                person.getId(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getDepartment(),
                person.getPhone(),
                person.getMobilPhone());

        return person;
    }

    @Override
    public List<Person> findAll() {

        return jdbcTemplate.query(
                "select id, first_name, middle_name, last_name, department, phone, mobil_phone " +
                        "from person",
                this::mapRowToPerson);
    }

    @Override
    public List<Person> findByAll(Map<String, Object> paramMap) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());



        List<Person> personList = namedParameterJdbcTemplate.query("select id, first_name, middle_name, last_name, department, phone, mobil_phone " +
                        "from person where first_name = :first_name " +
                        "or last_name = :last_name " +
                        "or department = :department " +
                        "or phone = :phone"
                , paramMap, this::mapRowToPerson).stream().collect(Collectors.toList());

        return personList;

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
                setMobilPhone(row.getLong(7)).
                build();

    }
}
