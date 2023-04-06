package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User

class UserMapper {

    @Autowired
    val mapper = ModelMapper()

    fun toEntity(dto: UserDto?): User? {
        return mapper.map(dto, User::class.java)
    }

    fun toDto(entity: User?): UserDto? {
        return mapper.map(entity, UserDto::class.java)
    }
}