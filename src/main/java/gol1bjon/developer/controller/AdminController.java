package gol1bjon.developer.controller;

import gol1bjon.developer.container.ComponentContainer;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.enums.AdminStatus;
import gol1bjon.developer.util.KeyboardButtonConstants;
import gol1bjon.developer.util.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class AdminController {
    public static void handleMessage(User user, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            handleText(user, message, text);
        } else if (message.hasPhoto()) {
            List<PhotoSize> photoSizeList = message.getPhoto();
            handlePhoto(user, message, photoSizeList);
        }
    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equals("/start")) {
            sendMessage.setText("Hello Admin");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getAdminMenu());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(KeyboardButtonConstants.ADD_CANDIDATE)) {

            ComponentContainer.adminStatusMap.put(chatId, AdminStatus.SEND_PHOTO);
            ComponentContainer.candidateMap.put(chatId, new Candidate());



            sendMessage.setText("Nomzodning rasmini yuboring");
        }else if (text.equals(KeyboardButtonConstants.START_ELECTION)) {

        }else if (text.equals(KeyboardButtonConstants.SHOW_COUNT_VOICE)) {

        }else if (text.equals(KeyboardButtonConstants.FINISH_ELECTION)) {

        }else {
            if (ComponentContainer.adminStatusMap.containsKey(chatId)){
                AdminStatus adminStatus = ComponentContainer.adminStatusMap.get(chatId);
                if (adminStatus.equals(AdminStatus.ENTER_FULL_NAME)){
                    Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                    candidate.setFullName(text);


                    ComponentContainer.adminStatusMap.put(chatId,AdminStatus.ENTER_AGE);

                    sendMessage.setText(text+" yoshingizni kiriting:");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                } else if (adminStatus.equals(AdminStatus.ENTER_AGE)) {
                    int age = -8;
                    try {
                        age = Integer.parseInt(text);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (age<=1920 && age>= 2023){
                        sendMessage.setText("Yosh xato kiritildi.Qayta kiriting: ");
                    }
                }
            }
        }

    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());
    }

    private static void handlePhoto(User user, Message message, List<PhotoSize> photoSizeList) {
        String chatId = String.valueOf(message.getChatId());

        String fileId = photoSizeList.get(photoSizeList.size() - 1).getFileId();

        if (ComponentContainer.adminStatusMap.containsKey(chatId)){
            AdminStatus adminStatus = ComponentContainer.adminStatusMap.get(chatId);
            if (adminStatus.equals(AdminStatus.SEND_PHOTO)){
                Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                candidate.setFileId(fileId);

                ComponentContainer.adminStatusMap.put(chatId,AdminStatus.ENTER_FULL_NAME);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Ism Familiyani kiriting:");
            }
        }


    }
}
