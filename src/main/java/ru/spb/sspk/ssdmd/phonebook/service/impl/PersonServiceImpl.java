package ru.spb.sspk.ssdmd.phonebook.service.impl;

import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook.model.mapper.PersonListMapper;
import ru.spb.sspk.ssdmd.phonebook.model.mapper.PersonMapper;
import ru.spb.sspk.ssdmd.phonebook.repository.PersonRepository;
import ru.spb.sspk.ssdmd.phonebook.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private PersonMapper personMapper;
    private PersonListMapper personListMapper;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }


    @Override
    public List<PersonDto> findPerson(String answer) {


        return null;
    }

    @Override
    public PersonDto save(PersonDto personDto) {

        PersonDto person = null;

        person = personMapper.toDto(personRepository.save(personMapper.toEntity(personDto)));

        return person;
    }

    @Override
    public PersonDto update(PersonDto personDto) {

        PersonDto person = null;

        person = personMapper.toDto(personRepository.update(personMapper.toEntity(personDto)));

        return person;
    }

    @Override
    public List<PersonDto> findAll() {

        List<PersonDto> personList = null;

        personList = personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());

        return personList;
    }

    @Override
    public String findByAll(String answer) {

        List<PersonDto> personList = null;

        personList = personRepository.findByAll(answer).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());

        return personList.toString().replace("[", "")
                .replace("]", "")
                .replace(",", ",\n\n");
    }

    @Override
    public String deleteById(Long id) {

        personRepository.deleteById(id);

        return "managed to complete";
    }
}
