package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

@Component
class PersonMapper {

    @Autowired
    private val mapper = ModelMapper()

    fun toEntity(dto: PersonDto): Person {
        return mapper.map(dto, Person::class.java)
    }

    fun toDto(entity: Person): PersonDto {
        return mapper.map(entity, PersonDto::class.java)
    }
}