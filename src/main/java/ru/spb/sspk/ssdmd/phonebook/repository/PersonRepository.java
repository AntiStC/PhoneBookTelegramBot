package ru.spb.sspk.ssdmd.phonebook.repository;

import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {


    Person save(Person person);

    Person update(Person person);

    List<Person> findAll();

    List<Person> findByDepartment(String department);

    List<Person> findByFirstName(String firstname);

    List<Person> findByLastName(String lastname);

    Optional<Person> findByPhone(Integer phone);

    void deleteById(Long id);
}
