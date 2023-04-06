package ru.spb.sspk.ssdmd.phonebook_test.model.dto

data class UserDto(
    val userId: Long,
    val username: String,
) {

    override fun toString(): String {
        return "userId=$userId, username='$username'"
    }
}
