package gol1bjon.developer.controller;

import gol1bjon.developer.container.ComponentContainer;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class AdminController {
    public static void handleMessage(User user, Message message) {
        if(message.hasText()){
            String text = message.getText();
            handleText(user, message, text);
        }else if(message.hasPhoto()){
            List<PhotoSize> photoSizeList = message.getPhoto();
            handlePhoto(user, message, photoSizeList);
        }
    }

    private static void handlePhoto(User user, Message message, List<PhotoSize> photoSizeList) {

        String fileId = photoSizeList.get(photoSizeList.size() - 1).getFileId();

    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());
    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());
    }
}
