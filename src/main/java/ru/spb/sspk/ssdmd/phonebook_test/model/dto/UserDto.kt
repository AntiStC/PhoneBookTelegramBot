package ru.spb.sspk.ssdmd.phonebook_test.model.dto

import java.util.*

data class UserDto(
    val userId: Long,
    val username: String,
    val activityAt: Date
) {
    override fun toString(): String {
        return "UserDto(userId=$userId, username='$username', activityAt=$activityAt)"
    }
}
