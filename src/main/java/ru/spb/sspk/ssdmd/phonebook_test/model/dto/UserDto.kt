package ru.spb.sspk.ssdmd.phonebook_test.model.dto

data class UserDto(
    var id: Long = 0,
    var userId: Long = 0,
    var username: String = "",
    var password: String = "",
    var authentication: Boolean = false,
    var createAt: String = "",
    var activityAt: String = ""
) {

    override fun toString(): String {
        return "userId=$userId, " +
                "username=@$username, " +
                "password=$password, " +
                "authentication=$authentication, " +
                "activityAt=$activityAt,  "
    }
}
