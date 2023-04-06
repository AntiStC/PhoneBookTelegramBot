package ru.spb.sspk.ssdmd.phonebook_test.service

import org.springframework.stereotype.Service
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto

@Service
interface PersonService {

    fun findByFirstname(firstname: String): List<PersonDto>

    fun findByLastname(lastname: String): List<PersonDto>

    fun findByPhone(phone: String): PersonDto

    fun findByMobilPhone(mobilPhone: String): PersonDto

    fun findBySector(sector: String): List<PersonDto>

    fun findByDepartment(department: String): List<PersonDto>

    fun findByBlock(block: String): List<PersonDto>

    fun performingSearchByAllParameters(answer: String): String

    fun findAll(): List<PersonDto>
}