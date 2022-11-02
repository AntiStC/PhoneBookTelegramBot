package ru.spb.sspk.ssdmd.phonebook_test.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.sspk.ssdmd.phonebook_test.service.PersonService;
import ru.spb.sspk.ssdmd.phonebook_test.service.UserService;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final UserService userService;
    private final SendMessageBot sendMessageBot;

    @Value("${telegram.name}")
    private String botName;
    @Value("${telegram.token}")
    private String botToken;
    String code = "TheProfessionalSoftwareMaintenanceDepartmentInTheBestAndJAVAIsTheBestProgrammingLanguage";

    public TelegramBot(PersonService personService, InlineKeyboardMaker inlineKeyboardMaker, UserService userService, SendMessageBot sendMessageBot) {
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.userService = userService;
        this.sendMessageBot = sendMessageBot;
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
                SendMessage message = getCommandResponse(answer, userId);
                message.setChatId(chatId);
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
            }
        } else if (update.hasCallbackQuery()) {

            try {
                SendMessage message = getCommandResponse(update.getCallbackQuery().getData(),
                        update.getCallbackQuery().getFrom().getId());
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(message);
            } catch (TelegramApiException e) {
                log.error("", e);
            }
        }

    }

    SendMessage getCommandResponse(String answer, Long userId) {
        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.INFO.getCommand())) {
            return sendMessageBot.handleInfoCommand(answer);
        }

        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.FIND.getCommand())) {
            return sendMessageBot.handleFindCommand(answer);
        }

        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.CREATE.getCommand())) {
            return sendMessageBot.handleCreateCommand(answer);
        }

        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.UPDATE.getCommand())) {
            return sendMessageBot.handleUpdateCommand(answer);
        }

        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.DELETE.getCommand())) {
            return sendMessageBot.handleDeleteCommand(answer);
        }

        if (userId.equals(userService.findAll(userId)) &&
                answer.equals(EnumCommandBot.NOTFOUND.getCommand())) {
            return sendMessageBot.handleNotFoundCommand();
        }

        if (answer.equals(EnumCommandBot.START.getCommand())) {
            return sendMessageBot.handleStartCommand();
        }

        if (answer.equals(code)){
            return sendMessageBot.handleSaveUserBot(userId);
        }

        return sendMessageBot.handleStandardCommand(answer, userId);
    }

}
