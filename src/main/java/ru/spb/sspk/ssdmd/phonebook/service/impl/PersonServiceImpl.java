package ru.spb.sspk.ssdmd.phonebook.service.impl;

import lombok.Data;
import org.jvnet.hk2.annotations.Service;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook.repository.PersonRepository;

import java.util.List;

@Service
@Data
public class PersonServiceImpl {

    private final PersonRepository personRepository;

    public List<Person> getAll(){
        return personRepository.findAll();
    }
}
