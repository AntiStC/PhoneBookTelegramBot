package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

@Repository
interface PersonRepository: CrudRepository<Person, Long> {
}