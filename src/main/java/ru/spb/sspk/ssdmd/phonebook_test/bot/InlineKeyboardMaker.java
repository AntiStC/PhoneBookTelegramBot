package ru.spb.sspk.ssdmd.phonebook_test.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Как пользоваться?");
        inlineKeyboardButton1.setCallbackData(EnumCommandBot.INFO.getCommand());

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Выполнить поиск");
        inlineKeyboardButton2.setCallbackData(EnumCommandBot.NOTFOUND.getCommand());

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
