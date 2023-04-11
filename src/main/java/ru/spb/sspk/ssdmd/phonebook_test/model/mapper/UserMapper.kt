package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User

@Component
class UserMapper {

    lateinit var mapper: ModelMapper

    fun toDto(entity: User?): UserDto? {
        return mapper.map(entity, UserDto::class.java)
    }

    fun toDtoList(entityList: MutableList<User>): MutableList<UserDto?> {
        return entityList.map { toDto(it) }.toMutableList()
    }
}