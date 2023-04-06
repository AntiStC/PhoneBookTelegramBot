package ru.spb.sspk.ssdmd.phonebook_test.bot

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.spb.sspk.ssdmd.phonebook_test.service.PersonService
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService

class SendMessageBot(
    private val personService: PersonService,
    private val userService: UserService,
    private val inlineKeyboardMarker: InlineKeyboardMarker
) {

    fun handleStartCommand(userId: Long, username: String): SendMessage {
        val messageStart = SendMessage()
        userService.checkingForAuthenticationAndAccess(userId, username, null)
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
        messageStandard.text = personService.performingSearchByAllParameters(answer)
        messageStandard.replyMarkup = inlineKeyboardMarker.getKeyboard()
        return messageStandard
    }

    fun handleLackOfAccessOrAuthentication(userId: Long, username: String, answer: String): SendMessage {
        val messageAccess = SendMessage()
        messageAccess.text = userService.checkingForAuthenticationAndAccess(userId, username, answer)
        return messageAccess
    }

    fun handleHelpCommand(): SendMessage {
        val messageInfo = SendMessage()
        messageInfo.text = "Поиск выполняется по Фамилии, по Имени, по отделу. " +
                "Для поиска введите Имя или Фамилию или отдел." +
                "Список отделов для поиска: \n" +
                "А - администрация;\n" +
                "ФБ - финансово-бухгалтерский отдел;\n" +
                "ХО - отдел хозяйственного обеспечения;\n" +
                "БП - отдел бюджетного планирования и закупок;\n" +
                "КО - отдел кадрового обеспечения и делопроизводства;\n" +
                "ОПО - отдел сопровождения общего ПО;\n" +
                "ПП - отдел поддержки пользователей;\n" +
                "ИБ - отдел информационной безопасности;\n" +
                "СПО - отдел сопровождения специального ПО;\n" +
                "БД - отдел сопровождения баз данных;\n" +
                "ВД - отдел ведения документации;\n" +
                "Ю - юридический отдел;\n" +
                "КЦ - отдел контакт-центра;\n" +
                "ТИС - отдел тестирования информационных систем;\n"
        return messageInfo
    }
}