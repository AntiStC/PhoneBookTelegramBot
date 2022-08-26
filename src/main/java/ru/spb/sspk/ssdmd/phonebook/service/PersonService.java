package ru.spb.sspk.ssdmd.phonebook.service;

import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.List;

public interface PersonService {


    Person save(Person person);

    Person update(Person person);

    List<Person> findAll();

    List<Person> findByDepartment(String department);

    List<Person> findByFirstName(String firstname);

    List<Person> findByLastName(String lastname);

    Person findByPhone(Integer phone);

    void deleteById(Long id);
}
