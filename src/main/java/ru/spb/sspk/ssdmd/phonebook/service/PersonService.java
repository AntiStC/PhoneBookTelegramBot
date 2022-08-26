package ru.spb.sspk.ssdmd.phonebook.service;

import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;

import java.util.List;

public interface PersonService {


    PersonDto save(PersonDto personDto);

    PersonDto update(PersonDto personDto);

    List<PersonDto> findAll();

    List<PersonDto> findByDepartment(String department);

    List<PersonDto> findByFirstName(String firstname);

    List<PersonDto> findByLastName(String lastname);

    PersonDto findByPhone(Integer phone);

    String deleteById(Long id);
}
