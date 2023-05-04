package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    @Query(
        "SELECT p FROM Person p WHERE p.firstname = :firstname " +
                "OR p.lastname = :lastname " +
                "OR p.department = :department " +
                "OR p.block = :block " +
                "OR p.sector = :sector " +
                "OR p.mobilPhone = :mobilPhone " +
                "OR p.phone = :phone"
    )
    fun findByFirstnameOrLastnameOrDepartmentOrBlockOrSectorOrMobilPhoneOrPhone(
        @Param("firstname") firstname: String?,
        @Param("lastname") lastname: String?,
        @Param("department") department: String?,
        @Param("block") block: String?,
        @Param("sector") sector: String?,
        @Param("mobilPhone") mobilPhone: String?,
        @Param("phone") phone: String?
    ): List<Person>
}