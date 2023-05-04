package ru.spb.sspk.ssdmd.phonebook_test.model.mapper

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.UserBot

@Component
class UserMapper {

    private val mapper = ModelMapper()

    init {
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        mapper.typeMap(UserBot::class.java, UserDto::class.java)
    }

    fun toDto(entity: UserBot): UserDto {
        return mapper.map(entity, UserDto::class.java)
    }

    fun toDtoList(entityList: MutableList<UserBot>): MutableList<UserDto?> {
        return entityList.map { toDto(it) }.toMutableList()
    }
}