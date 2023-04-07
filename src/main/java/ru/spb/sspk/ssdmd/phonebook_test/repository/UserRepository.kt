package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User
import javax.management.relation.Role

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun existsByUserIdAndRole(userId: Long, role: String):Boolean

    fun existsByUserIdAndAuthenticationTrue(userId: Long):Boolean

}