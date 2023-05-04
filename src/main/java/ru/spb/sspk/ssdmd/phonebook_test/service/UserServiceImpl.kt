package ru.spb.sspk.ssdmd.phonebook_test.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.spb.sspk.ssdmd.phonebook_test.exception.EntityNotFoundException
import ru.spb.sspk.ssdmd.phonebook_test.bot.TelegramBot
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.UserDto
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.UserBot
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.UserMapper
import ru.spb.sspk.ssdmd.phonebook_test.repository.UserRepository
import java.security.SecureRandom
import java.util.*

@Component
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val logger: Logger = LoggerFactory.getLogger(TelegramBot::class.java)
) : UserService {


    override fun create(userId: Long, username: String): String {
        val userBot = UserBot()
        userBot.userId = userId
        userBot.username = username
        userBot.password = generatePassword(8)
        if (checkingUserPresence(userId)) "Вы уже зарегистрированны, введите пароль для подтверждения."
        else userRepository.save(userBot)
        return "Пользователь создан, обратитесь в отдел СПО для получения пароля."
    }

    override fun checkingUserPresence(userId: Long): Boolean {
        return userRepository.existsByUserId(userId)
    }

    override fun isUserAuthentication(userId: Long): Boolean {
        return userRepository.existsByUserIdAndAuthenticationTrue(userId)
    }

    override fun checkingForAuthenticationAndAccess(userId: Long, username: String, answer: String): String {
        return if (checkingUserPresence(userId) && isUserAuthentication(userId)) "Введите параметры для поиска!"
        else if (checkingUserPresence(userId)
            && !isUserAuthentication(userId)
            && checkingTheCorrectnessOfTheEnteredPassword(userId, answer)
        ) updateAuthentication(userId)
        else if (!checkingUserPresence(userId)) create(userId, username)
        else "Обратитесь в отдел СПО! Данные введены неверно!"
    }

    override fun findById(userId: Long): String {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }
        return user.toString()
    }

    override fun checkingUserRole(userId: Long): Boolean {
        return userRepository.existsByUserIdAndRole(userId, "admin")
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

    override fun updateActivityAtToUser(userId: Long): String {
        val user = userRepository.findUserBotByUserId(userId)
        user.activityAt = Date()
        userRepository.save(user)
        return "user update activity!"
    }

    private fun checkingTheCorrectnessOfTheEnteredPassword(userId: Long, password: String): Boolean {
        val user = userRepository.findUserBotByUserId(userId)
        return user.password == password
    }

    private fun updateAuthentication(userId: Long): String {
        val user: UserBot = userRepository.findUserBotByUserId(userId)
        user.authentication = true
        userRepository.save(user)
        return if (isUserAuthentication(userId)) "Введен правильный пароль. Введите параметры для поиска."
        else "Введен неверный пароль!"
    }

    override fun findAllUsers(): String {
        val users: List<UserDto> = userRepository.findAll().map { userBot -> userMapper.toDto(userBot) }.toList()
        return users.toString().removeSurrounding("[", "]").replace(",", ",\n\n")
    }

    @Scheduled(cron = "@daily")
    private fun checkingActivityAndResettingAuthorization() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -14)
        val cutoffDate = calendar.time
        val expiredUserBots = userRepository.findByActivityAtBeforeAndAuthenticationIsTrue(cutoffDate)
        expiredUserBots.forEach { it.authentication = false }
        userRepository.saveAll(expiredUserBots)
        logger.info("checkingActivityAndResettingAuthorization = $expiredUserBots")
    }

    @Scheduled(cron = "@monthly")
    private fun changePasswordAndAuthentication() {
        val userList = userRepository.findAll()
        val newPassword = generatePassword(8)

        userList.forEach { user ->
            user.password = newPassword
            user.authentication = false
        }
        userRepository.saveAll(userList)
        logger.info("changePasswordAndAuthentication = $userList")
    }
}