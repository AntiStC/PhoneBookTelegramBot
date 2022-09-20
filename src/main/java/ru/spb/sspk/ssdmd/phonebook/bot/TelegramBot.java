package ru.spb.sspk.ssdmd.phonebook.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.sspk.ssdmd.phonebook.service.PersonService;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final PersonService personService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    @Value("${telegram.name}")
    private String botName;
    @Value("${telegram.token}")
    private String botToken;

    public TelegramBot(PersonService personService, InlineKeyboardMaker inlineKeyboardMaker) {
        this.personService = personService;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
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
            long chat_id = update.getMessage().getChatId();

            try {
                SendMessage message = getCommandResponse(answer);
                message.enableHtml(true);
                message.setParseMode(ParseMode.HTML);
                message.setChatId(chat_id);
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
                SendMessage message = handleNotFoundCommand();
                message.setChatId(chat_id);
            }
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

    private SendMessage getCommandResponse(String answer) throws TelegramApiException {
        if (answer.equals(EnumCommandBot.INFO.getCommand())) {
            return handleInfoCommand(answer);
        }

        if (answer.equals(EnumCommandBot.START.getCommand())) {
            return handleStartCommand(answer);
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

    private SendMessage handleNotFoundCommand() {
        SendMessage message = new SendMessage();
        message.setText("Начните поиск!\nНо помните про заглавные буквы!");
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
                "администрация;\n" +
                "ФБО - финансово-бухгалтерский отдел;\n" +
                "ОХО - отдел хозяйственного обеспечения;\n" +
                "ОБПиЗ - отдел бюджетного планирования и закупок;\n" +
                "ОКОиД - отдел кадрового обеспечения и делопроизводства;\n" +
                "ОСОПО - отдел сопровождения общего ПО;\n" +
                "ОПП - отдел поддержки пользователей;\n" +
                "ОИБ - отдел информационной безопасности;\n" +
                "ОССПОиБД - отдел сопровождения специального ПО и баз данных;\n" +
                "ОВД - отдел ведения документации;\n" +
                "ЮО - юридический отдел;\n" +
                "ОКЦ - отдел контакт-центра;\n" +
                "ОТИС - отдел тестирования информационных систем;\n" +
                "(в следующих версиях будет больше изменений):)");
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
        SendMessage messageStandart = new SendMessage();
        messageStandart.setText(personService.findByAll(answer));
        messageStandart.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageStandart;
    }

    private SendMessage handleUpdateCommand(String answer) {
        SendMessage messageFind= new SendMessage();
        messageFind.setText(personService.findByAll(answer));
        messageFind.setReplyMarkup(inlineKeyboardMaker.getKeyBoard());
        return messageFind;
    }

    private SendMessage handleStartCommand(String answer) {
        SendMessage messageStart = new SendMessage();
        messageStart.setText("Добро пожаловать в телефонный справочник ССПК. " +
                "Поиск выполняется по Фамилии, по Имени, по отделу. " +
                "Для поиска введите Имя или Фамилию или отдел." +
                "Но помните ФИО пишется с большой буквы, " +
                "а отдел с маленькой(в следующих версиях будет больше изменений) :)\n\n" +
                "выполните поиск...");
        return messageStart;
    }

}
