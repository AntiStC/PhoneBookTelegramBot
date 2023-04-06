package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.authentication = true")
    fun findByUserIdAndAuthentication(userId: Long): User?

}