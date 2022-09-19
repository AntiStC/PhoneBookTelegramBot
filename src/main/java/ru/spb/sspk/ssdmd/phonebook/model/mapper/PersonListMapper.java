package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.mapstruct.Mapper;
import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.List;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface PersonListMapper {

    List<Person> toEntityList(List<PersonDto> personDtoList);

    List<PersonDto> toDtoList(List<Person> personList);
}
