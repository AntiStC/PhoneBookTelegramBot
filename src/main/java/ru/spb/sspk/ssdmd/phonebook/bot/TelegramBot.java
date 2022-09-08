package ru.spb.sspk.ssdmd.phonebook.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.sspk.ssdmd.phonebook.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final PersonService personService;

    @Value("${telegram.name}")
    private String botName;
    @Value("${telegram.token}")
    private String botToken;

    public TelegramBot(PersonService personService) {
        this.personService = personService;
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
            Long chatId = update.getMessage().getChatId();
            try {
                SendMessage message = getCommandResponse(answer);
                message.setChatId(chatId);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage getCommandResponse(String answer) throws TelegramApiException {
//        if (answer.equals(EnumCommandBot.INFO.getCommand())) {
//            return handleInfoCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.START.getCommand())) {
//            return handleStartCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.FIND.getCommand())) {
//            return handleFindCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.CREATE.getCommand())) {
//            return handleCreateCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.UPDATE.getCommand())) {
//            return handleUpdateCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.DELETE.getCommand())) {
//            return handleDeleteCommand();
//        }
//
//        if (answer.equals(EnumCommandBot.DROP.getCommand())) {
//            return handleDropCommand();
//        }

        return handleStandardCommand(answer);
    }

    private SendMessage handleStandardCommand(String answer) {
        SendMessage message = new SendMessage();
        message.setText(String.valueOf(personService.findByAll(answer)));
        message.setReplyMarkup(getKeyBoard());
        return message;
    }

    public static InlineKeyboardMarkup getKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Как пользоваться?");
        inlineKeyboardButton1.setCallbackData(EnumCommandBot.INFO.getCommand());

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Выполнить поиск");
        inlineKeyboardButton2.setCallbackData(EnumCommandBot.FIND.getCommand());

        List<List<InlineKeyboardButton>> keyBoardButtons = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        keyBoardButtons.add(keyboardButtonsRow1);
        keyBoardButtons.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(keyBoardButtons);

        return inlineKeyboardMarkup;
    }
}
