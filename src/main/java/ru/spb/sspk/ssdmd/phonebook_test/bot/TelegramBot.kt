package ru.spb.sspk.ssdmd.phonebook_test.bot

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.spb.sspk.ssdmd.phonebook_test.service.UserServiceImpl

@Component
class TelegramBot(
    @Value("\${telegram.token}")
    private val botToken: String,
    @Value("\${telegram.name}")
    private val botUsername: String,
    private val userService: UserServiceImpl,
    private val sendMessageBot: SendMessageBot,
    private val logger: Logger = LoggerFactory.getLogger(TelegramBot::class.java)
) : TelegramLongPollingBot(botToken) {

    override fun getBotUsername(): String {
        return botUsername
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val answer: String = update.message.text
            val userId: Long = update.message.from.id
            val userName: String = update.message.from.userName
            val chatId: Long = update.message.chatId
            try {
                val message = getCommandResponse(answer, userId, userName)
                message.setChatId(chatId)
                execute(message)
                logger.info("Write message: user=$userId, $userName, '$answer'")
            } catch (e: TelegramApiException) {
                logger.error("", e)
            }
        } else if (update.hasCallbackQuery()) {
            val queryData: String = update.callbackQuery.data
            val userId: Long = update.callbackQuery.from.id
            val userName: String = update.callbackQuery.from.userName
            val chatId: Long = update.callbackQuery.message.chatId
            logger.info("InlineKeyBoard click: user=$userId, $userName, $queryData")
            try {
                val message = getCommandResponse(queryData, userId, userName)
                message.setChatId(chatId)
                execute(message)
            } catch (e: TelegramApiException) {
                logger.error("", e)
            }
        }
    }

    private fun getCommandResponse(answer: String, userId: Long, username: String): SendMessage {

        return if (userService.isUserAuthentication(userId) && answer.isNotEmpty()) {
            if (userService.checkingUserRole(userId) && answer == "/users") sendMessageBot.showAllUsersForAdmin()
            else {
                when (answer) {
                    "/start" -> sendMessageBot.handleStartCommand(userId, username)
                    "/help" -> sendMessageBot.handleHelpCommand(userId)
                    "/users" -> sendMessageBot.executionOfAccessDenied(userId, username)
                    else -> sendMessageBot.handleStandardCommand(userId, answer)
                }
            }
        } else if (userService.checkingUserPresence(userId) && answer.isNotEmpty()) {
            when (answer) {
                "/start" -> sendMessageBot.handleStartCommand(userId, username)
                "/help" -> sendMessageBot.handleHelpCommandNotAuthentication()
                "/users" -> sendMessageBot.executionOfAccessDenied(userId, username)
                else -> sendMessageBot.handleLackOfAccessOrAuthentication(userId, username, answer)
            }
        } else if (!userService.checkingUserPresence(userId)) {
            when {
                answer == "/start" -> sendMessageBot.handleStartCommand(userId, username)
                answer == "/help" -> sendMessageBot.handleHelpCommandNotAuthentication()
                answer.isNotEmpty() -> sendMessageBot.executionOfAccessDenied(userId, username)
                else -> sendMessageBot.handleNotFound()
            }
        } else sendMessageBot.handleNotFound()
    }
}
