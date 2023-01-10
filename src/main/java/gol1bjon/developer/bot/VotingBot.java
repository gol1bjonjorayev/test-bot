package gol1bjon.developer.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class VotingBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Test Uchun";
    }

    @Override
    public String getBotToken() {
        return "5873595498:AAF25vYZgeYDF0fn-NdOG8R00jbd4BJcRvU";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
