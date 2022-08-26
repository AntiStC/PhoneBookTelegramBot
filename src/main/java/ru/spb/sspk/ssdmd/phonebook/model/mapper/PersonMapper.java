package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonDto toDto(Person person) {

        PersonDto personDto = new PersonDto.Builder()
                .setId(person.getId())
                .setFirstName(person.getFirstName())
                .setMiddleName(person.getMiddleName())
                .setLastName(person.getLastName())
                .setDepartment(person.getDepartment())
                .setPhone(person.getPhone())
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
                .build();

        return person;
    }

    public static List<PersonDto> toListPerson(List<Person> personList) {

        return new ArrayList<>((personList).stream().map(PersonMapper::toDto)
                .collect(Collectors.toList()));
    }
}
