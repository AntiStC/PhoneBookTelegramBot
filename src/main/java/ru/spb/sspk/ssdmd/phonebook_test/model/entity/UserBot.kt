package ru.spb.sspk.ssdmd.phonebook_test.model.entity

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Pattern

@Entity
data class UserBot(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0,
    var userId: Long = 0,
    var username: String = "",
    var password: String = "",
    var authentication: Boolean = false,
    @Pattern(regexp = "\\A(user|admin)\\z") var role: String = "user",
    @DateTimeFormat var createAt: Date = Date.from(Instant.now()),
    @DateTimeFormat var activityAt: Date = Date.from(Instant.now())
)
