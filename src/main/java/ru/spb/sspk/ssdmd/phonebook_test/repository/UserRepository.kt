package ru.spb.sspk.ssdmd.phonebook_test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.UserBot
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserBot, Long> {

    fun existsByUserId(userId: Long): Boolean

    fun existsByUserIdAndRole(userId: Long, role: String): Boolean

    fun existsByUserIdAndAuthenticationTrue(userId: Long): Boolean

    fun findUserBotByUserId(userId: Long): UserBot

    fun findByActivityAtBeforeAndAuthenticationIsTrue(twoWeeksAgo: Date): List<UserBot>

    @Modifying
    @Query("UPDATE UserBot u SET u.authentication = true WHERE u.userId = :userId")
    fun updateAuthenticationByUserId(@Param("userId") userId: Long)

    @Modifying
    @Query("UPDATE UserBot SET activityAt = :activityAt WHERE userId = :userId")
    fun updateActivityAtByUserId(userId: Long, activityAt: Date)

    fun findByRole(role: String): List<UserBot>

}