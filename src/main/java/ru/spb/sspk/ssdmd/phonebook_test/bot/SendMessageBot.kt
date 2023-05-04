package ru.spb.sspk.ssdmd.phonebook_test.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.spb.sspk.ssdmd.phonebook_test.service.PersonService
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService

@Component
class SendMessageBot(
    private val personService: PersonService,
    private val userService: UserService,
    private val inlineKeyboardMarker: InlineKeyboardMarker
) {

    fun handleStartCommand(userId: Long, username: String): SendMessage {
        val messageStart = SendMessage()
        userService.checkingForAuthenticationAndAccess(userId, username, answer = "")
        messageStart.text =
            "Добро пожаловать в телефонный справочник ССПК. " +
                    "Для начала использования получите пароль в отделе СПО. " +
                    "Для продолжения работы введите параметры для поиска, " +
                    "это может быть имя, фамилия, блок, отдел, сектор, внутренний номер."
        return messageStart
    }

    fun handleNotFound(): SendMessage {
        val messageNotFound = SendMessage()
        messageNotFound.text = "Введите данные для поиска!"
        return messageNotFound
    }

    fun handleStandardCommand(userId: Long, answer: String): SendMessage {
        val messageStandard = SendMessage()
        userService.updateActivityAtToUser(userId)
        messageStandard.text = personService.performingSearchByAllParameters(answer).ifEmpty { "ничего не найдено" }
        messageStandard.replyMarkup = inlineKeyboardMarker.getKeyboard()
        return messageStandard
    }

    fun handleLackOfAccessOrAuthentication(userId: Long, username: String, answer: String): SendMessage {
        val messageAccess = SendMessage()
        messageAccess.text = userService.checkingForAuthenticationAndAccess(userId, username, answer)
        return messageAccess
    }

    fun handleHelpCommand(userId: Long): SendMessage {
        val messageInfo = SendMessage()
        userService.updateActivityAtToUser(userId)
        messageInfo.text = "Для поиска введите Имя или Фамилию или отдел. \n" +
                "Список отделов для поиска: \n" +
                "А - администрация;\n" +
                "ПСБ - отдел поддержки систем безопасности;\n" +
                "КЦ - отдел контакт-центра;\n" +
                "УПП - управление поддержки пользователей;\n" +
                "ПКС - отдел поддержки кадровых систем;\n" +
                "ПОС - отдел поддержки отраслевых систем;\n" +
                "ПЖСС - отдел поддержки жилищных и судебных систем;\n" +
                "ПГС - отдел поддержки геоинформационных систем;\n" +
                "ППР - отдел поддержки портальных решений;\n" +
                "ПРМ - отдел поддержки радочих мест;\n" +
                "ОПО - отдел сопровождения общего ПО;\n" +
                "БД - отдел сопровождения баз данных;\n" +
                "СПО - отдел сопровождения специального ПО;\n" +
                "ИБ - отдел информационной безопасности;\n" +
                "МОС - отдел аналитического и методического обеспечения сопровождения;\n" +
                "ФБ - финансово-бухгалтерский отдел;\n" +
                "ХО - отдел хозяйственного обеспечения;\n" +
                "БП - отдел бюджетного планирования и закупок;\n" +
                "КО - отдел по работе с персоналом;\n" +
                "С - сектор делопроизводства и секретариата;\n" +
                "Ю - юридический отдел;\n"
        return messageInfo
    }

    fun handleHelpCommandNotAuthentication(): SendMessage {
        val messageInfo = SendMessage()
        messageInfo.text = "Вы не авторизованы! " +
                "Пожалуйста введите пароль. " +
                "Для получения пароля обратитесь в отдел СПО.\n"
        return messageInfo
    }

    fun showAllUsersForAdmin(): SendMessage {
        val messageAllUsers = SendMessage()
        messageAllUsers.text = userService.findAllUsers()
        return messageAllUsers
    }

    fun executionOfAccessDenied(userId: Long, username: String): SendMessage {
        val messageAccess = SendMessage()
        userService.create(userId, username)
        messageAccess.text = "Отказано в доступе! Обратитесь в отдел СПО!"
        return messageAccess
    }
}