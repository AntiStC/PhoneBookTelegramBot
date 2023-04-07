package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    fun findByBlock(block: String): MutableList<Person>

    fun findByDepartment(department: String): MutableList<Person>

    fun findBySector(sector: String): MutableList<Person>

    fun findByMobilPhone(mobilPhone: String): Person

    fun findByPhone(phone: String): Person

    fun findByFirstname(firstname: String): MutableList<Person>

    fun findByLastname(lastname: String): MutableList<Person>
}