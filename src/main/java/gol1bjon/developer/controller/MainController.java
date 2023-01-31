package gol1bjon.developer.controller;

import gol1bjon.developer.container.ComponentContainer;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.entity.Customer;
import gol1bjon.developer.service.CandidateService;
import gol1bjon.developer.service.CustomerService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class MainController {
    public static void handleMessage(User user, Message message) {

        if(message.hasText()){
            String text = message.getText();
            handleText(user, message, text);
        }else if(message.hasContact()){
            Contact contact = message.getContact();
            handleContact(user, message, contact);
        }
    }

    private static void handleContact(User user, Message message, Contact contact) {
        String chatId = String.valueOf(message.getChatId());
    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());

        if (text.equals("/start")){

        }
    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        if (data.startsWith("voting/")){
            String[] split = data.split("/");
            String candidateId = split[1];

            Candidate candidateById = CandidateService.getCandidateById(candidateId);
            Customer customerByChatId = CustomerService.getCustomerByChatId(chatId);

//            if (customerByChatId.getCandidateId()==null){
//                customerByChatId.setCandidateId(candidateId);
//                candidateById.setCountVotes(candidateById.getCountVotes()+1);
//            }else {
//                SendMessage sendMessage = new SendMessage(chatId,"Siz bu candidatega avval ovoz bergansz!");
//                ComponentContainer.MY_BOT.sendMsg(sendMessage);
//            }
        }
    }


}
