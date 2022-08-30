package ru.spb.sspk.ssdmd.phonebook.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.sspk.ssdmd.phonebook.bot.nonCommand.NonCommand;
import ru.spb.sspk.ssdmd.phonebook.util.Utils;


public class TelegramBot extends TelegramLongPollingCommandBot {
    private Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private final NonCommand nonCommand;

    public TelegramBot(String botName, String botToken) {
        super();
        logger.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        logger.debug("Имя и токен присвоены");

        this.nonCommand = new NonCommand();
        logger.debug("Класс обработки сообщения, не являющегося командой, создан");

        register(new StartCommand("start", "Информация"));
        logger.debug("Команда start создана");

        register(new FirstNameCommand("firstname", "Поиск по имени"));
        logger.debug("Команда firstname создана");

        register(new LastNameCommand("lastname", "Поиск по фамилии"));
        logger.debug("Команда lastname создана");

        register(new DepartmentCommand("department", "Поиск по отделу"));
        logger.debug("Команда department создана");

        register(new FindAllCommand("findAll", "Показать все контакты"));
        logger.debug("Команда findAll создана");

        register(new CreateCommand("create", "Создать новый контакт"));
        logger.debug("Команда create создана");

        register(new UpdateCommand("update", "Изменить существующий контакт"));
        logger.debug("Команда update создана");

        register(new DeleteCommand("delete", "Удалить контакт"));
        logger.debug("Команда delete создана");

        register(new DropCommand("drop", "Удалить все контакты"));
        logger.debug("Команда drop создана");

    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotName() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String userName = Utils.getUserName(message);

        String answer = nonCommand.nonCommandExecute(chatId, userName, message.getText());
        setAnswer(chatId, userName, answer);
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s",
                    e.getMessage()));
            e.printStackTrace();
        }
    }
}
