package ru.spb.sspk.ssdmd.phonebook.bot;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

public class PhoneBookBot extends SpringWebhookBot {

    private String botPath;
    private String botUserName;

    private MessageHandler messageHandler;
    private CallbackQueryHandler callbackQueryHandler;

    public PhoneBookBot(SetWebhook setWebhook, MessageHandler messageHandler,
                        CallbackQueryHandler callbackQueryHandler){
        super(setWebhook);
        this.messageHandler=messageHandler;
        this.callbackQueryHandler=callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebHookUpdateReceived(Update update){
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) throws IOException {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage());
            }
        }
        return null;
    }
}
