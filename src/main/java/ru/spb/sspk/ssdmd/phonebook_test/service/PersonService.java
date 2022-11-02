package ru.spb.sspk.ssdmd.phonebook_test.service;

import org.springframework.stereotype.Service;
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto;

import java.util.List;

@Service
public interface PersonService {

    PersonDto save(String answer);

    PersonDto update(String answer);

    List<PersonDto> findAll();

    String findByAll(String answer);

    String deleteById(Long id);

    public List<PersonDto> findPerson(String answer);

}
