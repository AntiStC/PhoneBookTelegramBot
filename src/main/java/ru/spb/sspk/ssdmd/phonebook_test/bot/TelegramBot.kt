package ru.spb.sspk.ssdmd.phonebook_test.bot

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.spb.sspk.ssdmd.phonebook_test.config.BotProperties
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService

@Component
class TelegramBot(
    val botProperties: BotProperties,
    val userService: UserService,
    val sendMessageBot: SendMessageBot,
    val logger: Logger = LoggerFactory.getLogger(TelegramBot::class.java)
) : TelegramLongPollingBot() {

    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.name
    }

    override fun onUpdateReceived(update: Update) {

        if (update.hasMessage() && update.message.hasText()) {
            val answer: String = update.message.text
            val userId: Long = update.message.from.id
            val userName: String = update.message.from.userName
            val chatId: Long = update.message.chatId
            try {
                val message: SendMessage = getCommandResponse(answer, userId, userName)
                message.setChatId(chatId)
                execute(message)
            } catch (e: TelegramApiException) {
                logger.error("", e)
            }
        } else if (update.hasCallbackQuery()) {
            val queryData = update.callbackQuery.data
            val userId: Long = update.callbackQuery.from.id
            val userName = update.callbackQuery.from.userName
            val chatId = update.callbackQuery.message.chatId
            try {
                val message: SendMessage = getCommandResponse(queryData, userId, userName)
                message.setChatId(chatId)
                execute(message)
            } catch (e: TelegramApiException) {
                logger.error("", e)
            }
        }
    }

    private fun getCommandResponse(answer: String, userId: Long, username: String): SendMessage {

        return when {
            answer == "/start" -> sendMessageBot.handleStartCommand(userId, username)
            userService.checkingUserPresence(userId) && userService.checkingForAuthenticationNow(userId) ->
                when (answer) {
                    "/help" -> sendMessageBot.handleHelpCommand()
                    null -> sendMessageBot.handleNotFound()
                    else -> sendMessageBot.handleStandardCommand(userId, answer)
                }
//            userService.checkingUserRole(userId) && userService.checkingForAuthenticationNow(userId)->
//                when(answer){
//                    "/usersAll"->
//                }

            else -> sendMessageBot.handleLackOfAccessOrAuthentication(userId, username, answer)
        }
    }
}
