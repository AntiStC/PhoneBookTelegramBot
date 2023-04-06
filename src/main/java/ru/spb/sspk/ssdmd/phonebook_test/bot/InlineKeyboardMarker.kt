package ru.spb.sspk.ssdmd.phonebook_test.bot

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

class InlineKeyboardMarker {

    fun getKeyboard(): InlineKeyboardMarkup {

        val keyboardMarkup = InlineKeyboardMarkup()

        val keyboardButtonHelp = InlineKeyboardButton()
        keyboardButtonHelp.text = "Помощь"
        keyboardButtonHelp.callbackData = "/help"

        val keyBoardButtons: MutableList<List<InlineKeyboardButton>> = ArrayList()
        val keyboardButtonsRow2: MutableList<InlineKeyboardButton> = ArrayList()
        keyboardButtonsRow2.add(keyboardButtonHelp)

        keyBoardButtons.add(keyboardButtonsRow2)

        keyboardMarkup.keyboard = keyBoardButtons

        return getKeyboard()
    }
}

