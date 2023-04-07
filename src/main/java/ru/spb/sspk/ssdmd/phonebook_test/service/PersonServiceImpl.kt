package ru.spb.sspk.ssdmd.phonebook_test.service

import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.PersonMapper
import ru.spb.sspk.ssdmd.phonebook_test.repository.PersonRepository
import java.util.*

class PersonServiceImpl(
    private val personRepository: PersonRepository,
    private val personMapper: PersonMapper
) : PersonService {

    override fun performingSearchByAllParameters(answer: String): String {
        TODO("Not yet implemented")
    }

    private fun findByFirstname(firstname: String): MutableList<PersonDto> {
        var firstname = firstname.lowercase(Locale.getDefault())
        firstname.replaceFirstChar {
            if
                    (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
        return personMapper.toDtoList(personRepository.findByFirstname(firstname))
    }

    private fun findByLastname(lastname: String): MutableList<PersonDto> {
        val parts = lastname.lowercase(Locale.getDefault()).split("-")
        val capitalizedParts =
            parts.map {
                it.replaceFirstChar {
                    if
                            (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
        val capitalizedLastname = capitalizedParts.joinToString("-")
        return personMapper.toDtoList(personRepository.findByLastname(capitalizedLastname))
    }

    private fun findByPhone(phone: String): PersonDto {
        return personMapper.toDto(personRepository.findByPhone(phone))
    }

    private fun findByMobilPhone(mobilPhone: String): PersonDto {
        var mobilPhone = mobilPhone
        if (mobilPhone.startsWith("8")) mobilPhone = mobilPhone.removePrefix("8")
        else if (mobilPhone.startsWith("+7")) mobilPhone = mobilPhone.removePrefix("+7")
        else if (mobilPhone.startsWith("7")) mobilPhone = mobilPhone.removePrefix("7")
        return personMapper.toDto(personRepository.findByMobilPhone(mobilPhone))
    }

    private fun findBySector(sector: String): MutableList<PersonDto> {
        return personMapper.toDtoList(personRepository.findBySector(sector.uppercase(Locale.getDefault())))
    }

    private fun findByDepartment(department: String): MutableList<PersonDto> {
        return personMapper.toDtoList(personRepository.findByDepartment(department.uppercase(Locale.getDefault())))
    }

    private fun findByBlock(block: String): List<PersonDto> {
        return personMapper.toDtoList(personRepository.findByBlock(block.uppercase(Locale.getDefault())))
    }

    private fun findAll(): MutableList<PersonDto> {
        return personMapper.toDtoList(personRepository.findAll().toMutableList())
    }
}