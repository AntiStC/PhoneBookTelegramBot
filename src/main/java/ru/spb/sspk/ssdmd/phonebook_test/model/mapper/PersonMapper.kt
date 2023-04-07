package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

class PersonMapper {

    @Autowired
    lateinit var mapper: ModelMapper

    fun toDto(entity: Person): PersonDto {
        return mapper.map(entity, PersonDto::class.java)
    }

    fun toDtoList(entityList: MutableList<Person>): MutableList<PersonDto> {
        return entityList.map { toDto(it) }.toMutableList()
    }
}