package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

@Component
public class PersonMapper {

    public static PersonDto toDto(Person person) {

        PersonDto personDto = new PersonDto.Builder()
                .setId(person.getId())
                .setFirstName(person.getFirstName())
                .setMiddleName(person.getMiddleName())
                .setLastName(person.getLastName())
                .setDepartment(person.getDepartment())
                .setPhone(person.getPhone())
                .setMobilPhone(person.getMobilPhone())
                .build();

        return personDto;
    }

    public static Person toEntity(PersonDto personDto) {

        Person person = new Person.Builder()
                .setId(personDto.getId())
                .setFirstName(personDto.getFirstName())
                .setMiddleName(personDto.getMiddleName())
                .setLastName(personDto.getLastName())
                .setDepartment(personDto.getDepartment())
                .setPhone(personDto.getPhone())
                .setMobilPhone(personDto.getMobilPhone())
                .build();

        return person;
    }
}
