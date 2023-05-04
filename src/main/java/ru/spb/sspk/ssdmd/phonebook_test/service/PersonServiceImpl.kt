package ru.spb.sspk.ssdmd.phonebook_test.service

import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.PersonMapper
import ru.spb.sspk.ssdmd.phonebook_test.repository.PersonRepository
import java.util.*

@Component
class PersonServiceImpl(
    private val personRepository: PersonRepository,
    private val personMapper: PersonMapper
) : PersonService {

    override fun performingSearchByAllParameters(answer: String): String {
        val lastname: String = if ('-' in answer) {
            answer.lowercase()
            answer.split('-').joinToString("-") {
                it.replaceFirstChar {if (it.isLowerCase()) it.titlecase() else it.toString()}}
        } else answer.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        val firstname = answer.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val department = answer.uppercase(Locale.getDefault())
        val block = answer.uppercase(Locale.getDefault())
        val sector = answer.uppercase(Locale.getDefault())
        val phone = if (answer.all { it.isDigit() }) answer
        else null

        val mobilPhone = if (answer.any() { it.isDigit() }) {
            if (answer.startsWith("8")) answer.removePrefix("8")
            else if (answer.startsWith("+7")) answer.removePrefix("+7")
            else answer.removePrefix("7")
        } else null
        val persons: List<PersonDto> =
            personRepository.findByFirstnameOrLastnameOrDepartmentOrBlockOrSectorOrMobilPhoneOrPhone(
                firstname,
                lastname,
                department,
                block,
                sector,
                mobilPhone,
                phone
            ).map { person -> personMapper.toDto(person) }.toList()
        return persons.toString().removeSurrounding("[", "]").replace(",", ",\n\n")
    }
}