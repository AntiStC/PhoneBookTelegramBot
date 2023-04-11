package ru.spb.sspk.ssdmd.phonebook_test.service

interface UserService {

    fun create(userId: Long, username: String): String

    fun checkingUserPresence(userId: Long): Boolean

    fun checkingForAuthenticationNow(userId: Long): Boolean

    fun checkingForAuthenticationAndAccess(userId: Long, username: String, answer: String?): String

    fun findById(userId: Long): String

    fun checkingUserRole(userId: Long): Boolean

    fun updateActivityAtToUser(userId: Long)

    fun findAllUsers(): String
}