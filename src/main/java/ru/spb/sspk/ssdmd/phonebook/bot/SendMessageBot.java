package ru.spb.sspk.ssdmd.phonebook.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.spb.sspk.ssdmd.phonebook.service.PersonService;
import ru.spb.sspk.ssdmd.phonebook.service.UserService;

@Component
public class SendMessageBot {

    private PersonService personService;
    private InlineKeyboardMaker inlineKeyboardMaker;
    private UserService userService;

    public SendMessageBot(PersonService personService, InlineKeyboardMaker inlineKeyboardMaker, UserService userService) {
        this.personService = personService;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.userService = userService;
    }


    SendMessage handleNotFoundCommand() {
        SendMessage message = new SendMessage();
        message.setText("Начните поиск!\n");
        return message;
    }

    SendMessage handleStandardCommand(String answer, Long userId) {
        SendMessage messageStandart = new SendMessage();
        if (userId.equals(userService.findAll(userId))) {
            messageStandart.setText(personService.findByAll(answer));
            messageStandart.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
            return messageStandart;
        }else {
            messageStandart.setText("Нет права доступа!");
            return messageStandart;
        }
    }

    SendMessage handleInfoCommand(String answer) {
        SendMessage messageInfo = new SendMessage();
        messageInfo.setText("Поиск выполняется по Фамилии, по Имени, по отделу. " +
                "Для поиска введите Имя или Фамилию или отдел." +
                "Но помните ФИО пишется с большой буквы." +
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
                "ТИС - отдел тестирования информационных систем;\n");
        messageInfo.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());

        return messageInfo;
    }

    SendMessage handleCreateCommand(String answer) {
        SendMessage messageCreate = new SendMessage();
        personService.save(answer);
        messageCreate.setText("контакт успешно создан");
        messageCreate.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageCreate;
    }

    SendMessage handleDeleteCommand(String answer) {
        SendMessage messageDelete = new SendMessage();
        personService.deleteById(Long.valueOf(answer));
        messageDelete.setText("контакт успешно удален");
        messageDelete.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageDelete;
    }

    SendMessage handleFindCommand(String answer) {
        SendMessage messageFind = new SendMessage();
        messageFind.setText(personService.findByAll(answer));
        messageFind.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageFind;
    }

    SendMessage handleUpdateCommand(String answer) {
        SendMessage messageFind = new SendMessage();
        messageFind.setText(personService.findByAll(answer));
        messageFind.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageFind;
    }

    SendMessage handleStartCommand() {

        SendMessage messageStartCommand = new SendMessage();
        messageStartCommand.setText("Введите кодовую фразу!");

        return messageStartCommand;
    }

    SendMessage handleSaveUserBot(Long userId) {
        SendMessage messageSaveUserCommand = new SendMessage();
        userService.save(userId);
        messageSaveUserCommand.setText("Теперь можно пользоваться!");
        messageSaveUserCommand.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());

        return messageSaveUserCommand;
    }
}