package ru.spb.sspk.ssdmd.phonebook_test.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.sspk.ssdmd.phonebook_test.service.PersonService;
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final PersonService personService;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final UserService userService;

    @Value("${telegram.name}")
    private String botName;
    @Value("${telegram.token}")
    private String botToken;

    public TelegramBot(PersonService personService, InlineKeyboardMaker inlineKeyboardMaker, UserService userService) {
        this.personService = personService;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String answer = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();
            String userFirstName = update.getMessage().getFrom().getFirstName();
            String userLastName = update.getMessage().getFrom().getLastName();
            Long chatId = update.getMessage().getChatId();
//            if (!userId.equals(userService.findAll(userId))) {
//                SendMessage message = handleUserNotFound(userId);
//                message.setChatId(chatId);
//            } else if (answer.toUpperCase().equals(responce)) {
//                userService.save(userId, userFirstName, userLastName);
//            } else {
            try {
                SendMessage message = getCommandResponse(
                        answer);
                message.setChatId(chatId);
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
                SendMessage message = handleNotFoundCommand();
                message.setChatId(chatId);
            }
//                            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage message = getCommandResponse(update.getCallbackQuery().getData());
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
            }
        }
    }

    private SendMessage getCommandResponse(String answer)
            throws TelegramApiException{
        if (answer.equals(EnumCommandBot.INFO.getCommand())) {
            return handleInfoCommand(answer);
        }

        if (answer.equals(EnumCommandBot.FIND.getCommand())) {
            return handleFindCommand(answer);
        }

        if (answer.equals(EnumCommandBot.CREATE.getCommand())) {
            return handleCreateCommand(answer);
        }

        if (answer.equals(EnumCommandBot.UPDATE.getCommand())) {
            return handleUpdateCommand(answer);
        }

        if (answer.equals(EnumCommandBot.DELETE.getCommand())) {
            return handleDeleteCommand(answer);
        }

        if (answer.equals(EnumCommandBot.NOTFOUND.getCommand())) {
            return handleNotFoundCommand();
        }

        return handleStandardCommand(answer);
    }

    private SendMessage getCommandResponse(
            String answer, Long userId, String userFirstName, String userLastName)
            throws TelegramApiException {

        if (answer.equals(EnumCommandBot.START.getCommand())) {
            return handleStartCommand(answer, userId);
        }
        else if  (answer.equals("сспкforever")){
            return handleUserNotFound(userId, userFirstName, userLastName);
        }
        else {
            return null;
        }
    }

    private SendMessage handleNotFoundCommand() {
        SendMessage message = new SendMessage();
        message.setText("Начните поиск!\n");
        return message;
    }

    private SendMessage handleStandardCommand(String answer) {
        SendMessage messageStandart = new SendMessage();
        messageStandart.setText(personService.findByAll(answer));
        messageStandart.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageStandart;
    }

    private SendMessage handleInfoCommand(String answer) {
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

    private SendMessage handleCreateCommand(String answer) {
        SendMessage messageCreate = new SendMessage();
        personService.save(answer);
        messageCreate.setText("контакт успешно создан");
        messageCreate.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageCreate;
    }

    private SendMessage handleDeleteCommand(String answer) {
        SendMessage messageDelete = new SendMessage();
        personService.deleteById(Long.valueOf(answer));
        messageDelete.setText("контакт успешно удален");
        messageDelete.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageDelete;
    }

    private SendMessage handleFindCommand(String answer) {
        SendMessage messageFind = new SendMessage();
        messageFind.setText(personService.findByAll(answer));
        messageFind.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageFind;
    }

    private SendMessage handleUpdateCommand(String answer) {
        SendMessage messageFind = new SendMessage();
        messageFind.setText(personService.findByAll(answer));
        messageFind.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageFind;
    }

    private SendMessage handleStartCommand(String answer, Long userId) {
        Long userIdInDB = userService.findAll(userId);
        SendMessage messageStart = new SendMessage();
        if (userId.equals(userIdInDB)) {
            messageStart.setText("Введите кодовую фразу для проверки!");
        } else {
            messageStart.setText("Добро пожаловать в телефонный справочник ССПК. " +
                    "Поиск выполняется по Фамилии, по Имени, по отделу. " +
                    "Для поиска введите Имя или Фамилию или отдел." +
                    "\n\n" +
                    "выполните поиск...");
        }
        return messageStart;
    }

    private SendMessage handleUserNotFound(
            Long userId, String userFirstName, String userLastName) {
        SendMessage messageUserNotFound = new SendMessage();
        userService.save(userId, userFirstName, userLastName);
        messageUserNotFound.setText("Теперь можно пользоваться!");
        messageUserNotFound.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());

        return messageUserNotFound;
    }

}
