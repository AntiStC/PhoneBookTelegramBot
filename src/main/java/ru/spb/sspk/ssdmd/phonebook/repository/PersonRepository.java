package ru.spb.sspk.ssdmd.phonebook.repository;

import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.List;
import java.util.Map;

public interface PersonRepository {


    Person save(Person person);

    Person update(Person person);

    List<Person> findAll();

    List<Person> findByAll(Map<String, Object> paramMap);

    void deleteById(Long id);
}
