package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

@Component
class PersonMapper {
    private val mapper = ModelMapper()

    init {
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        mapper.typeMap(Person::class.java, PersonDto::class.java)
    }

    fun toDto(person: Person): PersonDto {
        return mapper.map(person, PersonDto::class.java)
    }

    fun toEntity(personDto: PersonDto): Person {
        return mapper.map(personDto, Person::class.java)
    }

    fun toDtoList(persons: List<Person>): MutableList<PersonDto> {
        return persons.map { toDto(it) }.toMutableList()
    }
}