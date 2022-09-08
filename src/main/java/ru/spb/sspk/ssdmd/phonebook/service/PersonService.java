package ru.spb.sspk.ssdmd.phonebook.service;

import org.springframework.stereotype.Service;
import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;

import java.util.List;

@Service
public interface PersonService {

    List<PersonDto> findPerson(String answer);

    PersonDto save(PersonDto personDto);

    PersonDto update(PersonDto personDto);

    List<PersonDto> findAll();

    List<PersonDto> findByAll(String answer);

    String deleteById(Long id);
}
