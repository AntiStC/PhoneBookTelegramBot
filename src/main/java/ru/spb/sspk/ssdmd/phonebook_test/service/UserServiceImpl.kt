package ru.spb.sspk.ssdmd.phonebook_test.service

import ru.spb.sspk.ssdmd.phonebook_test.exception.EntityNotFoundException
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.UserMapper
import ru.spb.sspk.ssdmd.phonebook_test.repository.UserRepository
import java.security.SecureRandom
import java.time.Instant
import java.util.*

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserService {

    override fun create(userId: Long, username: String): String {
        val userDto = UserDto(userId, username)
        val user = User()
        user.userId = userDto.userId
        user.username = userDto.username
        user.password = generatePassword(8)
        userRepository.save(user)
        return if (userRepository.existsById(userId)) "create"
        else "null"
    }

    override fun checkingUserPresence(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

    override fun checkingForAuthenticationNow(userId: Long): Boolean {
        return userRepository.findByUserIdAndAuthentication(userId) != null
    }

    override fun checkingForAuthenticationAndAccess(userId: Long, username: String, answer: String?): String {
        val user = User()
        user.userId = userId
        user.username = username
        user.password = generatePassword(8)
        when {
            !checkingUserPresence(userId) || !checkingForAuthenticationNow(userId) ->
                if (!checkingUserPresence(userId)) create(userId, username)

            answer.equals(null) && checkingForAuthenticationNow(userId) ->
                checkingTheCorrectnessOfTheEnteredPassword(userId, answer!!)

            else -> false
        }
        return if (checkingUserPresence(userId) || checkingForAuthenticationNow(userId)) "Введите запрос"
        else if (checkingTheCorrectnessOfTheEnteredPassword(userId, answer!!)) "Введите запрос"
        else "обратитесь в отдел СПО для получения прав доступа"
    }

    override fun findById(userId: Long): String {
        val userDto =
            userMapper.toDto(userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") })
        return userDto.toString()
    }

    override fun checkingUserRole(userId: Long): Boolean {
        TODO("Not yet implemented")
    }

    private fun generatePassword(length: Int): String {
        val random = SecureRandom()
        val allowChar = "0123456789" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "!@#\$%^&*()_-+="
        return (1..length)
            .map { allowChar[random.nextInt(allowChar.length)] }
            .joinToString("")
    }

     override fun updateActivityAtToUser(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }
        user.activityAt = Date.from(Instant.now())
        userRepository.save(user)
    }

    private fun checkingTheCorrectnessOfTheEnteredPassword(userId: Long, password: String): Boolean {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }
        return user.password == password
    }
}