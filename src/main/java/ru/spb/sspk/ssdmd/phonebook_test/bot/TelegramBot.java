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

    private SendMessageBot sendMessageBot;

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
            Long chatId = update.getMessage().getChatId();
                try {
                    SendMessage message = sendMessageBot.getCommandResponse(answer);
                    message.setChatId(chatId);
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("", e);
                    SendMessage message = sendMessageBot.handleNotFoundCommand();
                    message.setChatId(chatId);
                }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage message = sendMessageBot.getCommandResponse(update.getCallbackQuery().getData());
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
            }
        }
    }
}
