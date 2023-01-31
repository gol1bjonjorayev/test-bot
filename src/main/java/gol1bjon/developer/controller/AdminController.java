package gol1bjon.developer.controller;

import gol1bjon.developer.container.ComponentContainer;
import gol1bjon.developer.db.DataBase;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.entity.Customer;
import gol1bjon.developer.enums.AdminStatus;
import gol1bjon.developer.files.WorkWithFiles;
import gol1bjon.developer.service.CandidateService;
import gol1bjon.developer.util.InlineButtonConstants;
import gol1bjon.developer.util.InlineKeyboardUtil;
import gol1bjon.developer.util.KeyboardButtonConstants;
import gol1bjon.developer.util.KeyboardButtonUtil;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(KeyboardButtonConstants.START_ELECTION)) {
            if (DataBase.candidateList.isEmpty()) {
                sendMessage.setText("Nomzodlar yo'q");
            } else if (ComponentContainer.startElection) {
                sendMessage.setText("Saylov davom etayapdi");
                sendMessageToCustomers("Saylov davom etayapdi");
            } else {
                ComponentContainer.startElection = true;
                sendMessage.setText("Saylov boshlandi");
                sendMessageToCustomers("Saylov boshlandi");
                sendMessageToCustomersForElection();

                StringBuilder sb = new StringBuilder("Umumiy Natijalar: \n\n");

                DataBase.candidateList.sort((c1, c2) -> Integer.compare(c2.getCountVotes(), c1.getCountVotes()));

                for (Candidate candidate : DataBase.candidateList) {
                    sb.append(candidate.getFullName() + " : " + candidate.getCountVotes() + "\n");
                }

                sendMessageToCustomers(sb.toString());

                SendMessage sendMessage1 = new SendMessage(chatId, sb.toString());
                ComponentContainer.MY_BOT.sendMsg(sendMessage1);

            }
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(KeyboardButtonConstants.SHOW_COUNT_VOICE)) {
            StringBuilder sb = new StringBuilder("Hozirgi vaqtdagi Natijalar: \n\n");

            DataBase.candidateList.sort((c1, c2) -> Integer.compare(c2.getCountVotes(), c1.getCountVotes()));

            for (Candidate candidate : DataBase.candidateList) {
                sb.append(candidate.getFullName() + " : " + candidate.getCountVotes() + "\n");
            }
            sendMessage.setText(sb.toString()+"\n"+ LocalDateTime.now()+ " paytiga ko'ra");
            ComponentContainer.MY_BOT.sendMsg(sendMessage);

        } else if (text.equals(KeyboardButtonConstants.FINISH_ELECTION)) {
            if (!ComponentContainer.startElection) {
                sendMessage.setText("Saylov umuman boshlanmagan.Uni yakunlash ma'nosiz");
            } else {
                ComponentContainer.startElection = false;
                sendMessage.setText("Saylov yakunlandi");
                sendMessageToCustomers("Saylov yakunlandi");

                DataBase.candidateList.clear();
                WorkWithFiles.writeCandidateList();
            }
            ComponentContainer.MY_BOT.sendMsg(sendMessage);

        } else {
            if (ComponentContainer.adminStatusMap.containsKey(chatId)) {
                AdminStatus adminStatus = ComponentContainer.adminStatusMap.get(chatId);
                if (adminStatus.equals(AdminStatus.ENTER_FULL_NAME)) {
                    Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                    candidate.setFullName(text);


                    ComponentContainer.adminStatusMap.put(chatId, AdminStatus.ENTER_AGE);

                    sendMessage.setText(text + " yoshingizni kiriting:");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                } else if (adminStatus.equals(AdminStatus.ENTER_AGE)) {
                    int age = -8;
                    try {
                        age = Integer.parseInt(text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (age <= 0) {
                        sendMessage.setText("Yosh xato kiritildi.Qayta kiriting: ");
                        ComponentContainer.MY_BOT.sendMsg(sendMessage);
                    } else {
                        Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                        candidate.setAge(age);

                        ComponentContainer.adminStatusMap.put(chatId, AdminStatus.ENTER_DESCRIPTION);

                        sendMessage.setText(candidate.getFullName() + " haqida qo'shimcha malumot kiriting:");
                        ComponentContainer.MY_BOT.sendMsg(sendMessage);
                    }

                } else if (adminStatus.equals(AdminStatus.ENTER_DESCRIPTION)) {
                    Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                    candidate.setDescription(text);


                    ComponentContainer.adminStatusMap.put(chatId, AdminStatus.CONFIRM_OR_CANCEL);

                    SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(candidate.getFileId()));
                    StringBuilder sb = new StringBuilder();

                    sb.append("To'liq FIO: " + candidate.getFullName() + "\n");
                    sb.append("Yosh: " + candidate.getAge() + "\n");
                    sb.append("Qo'shimcha malumot: " + candidate.getDescription() + "\n");

                    sendPhoto.setCaption(sb.toString());

                    sendPhoto.setReplyMarkup(InlineKeyboardUtil.getConfirmOrCancelMenu());

                    ComponentContainer.MY_BOT.sendMsg(sendPhoto);
                }
            }
        }

    }

    private static void sendMessageToCustomersForElection() {


        for (Customer customer : DataBase.customerList) {
            for (Candidate candidate : DataBase.candidateList) {

            }
        }
    }

    private static void sendMessageToCustomers(String message) {
        for (Customer customer : DataBase.customerList) {
            SendMessage sendMessage = new SendMessage(customer.getChatId(), message);
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        }
    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (data.equals(InlineButtonConstants.CONFIRM_CALL_BACK)) {
            Candidate candidate = ComponentContainer.candidateMap.get(chatId);

            CandidateService.addCandidate(candidate);

            sendMessage.setText(candidate.getFullName() + " muvaffiqiyatli saqlandi.");

        } else if (data.equals(InlineButtonConstants.CANCEL_CALL_BACK)) {
            Candidate candidate = ComponentContainer.candidateMap.get(chatId);
            sendMessage.setText(candidate.getFullName() + " saqlanmadi.");
        }

        ComponentContainer.adminStatusMap.remove(chatId);
        ComponentContainer.candidateMap.remove(chatId);

        ComponentContainer.MY_BOT.sendMsg(sendMessage);

        DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
        ComponentContainer.MY_BOT.sendMsg(deleteMessage);

    }

    private static void handlePhoto(User user, Message message, List<PhotoSize> photoSizeList) {
        String chatId = String.valueOf(message.getChatId());

        String fileId = photoSizeList.get(photoSizeList.size() - 1).getFileId();

        if (ComponentContainer.adminStatusMap.containsKey(chatId)) {
            AdminStatus adminStatus = ComponentContainer.adminStatusMap.get(chatId);
            if (adminStatus.equals(AdminStatus.SEND_PHOTO)) {
                Candidate candidate = ComponentContainer.candidateMap.get(chatId);
                candidate.setFileId(fileId);

                ComponentContainer.adminStatusMap.put(chatId, AdminStatus.ENTER_FULL_NAME);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Ism Familiyani kiriting:");
                ComponentContainer.MY_BOT.sendMsg(sendMessage);
            }
        }


    }
}

@AllArgsConstructor
class MyThread extends Thread{
    private  Customer customer;
    private Candidate candidate;

    @Override
    public void run() {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(customer.getChatId());
        sendPhoto.setPhoto(new InputFile(candidate.getFileId()));
        sendPhoto.setCaption(candidate.getFullName()+"\n "+ candidate.getDescription());

        sendPhoto.setReplyMarkup(InlineKeyboardUtil.getVotingMenu(candidate.getId(),candidate.getCountVotes()));


    }
}
