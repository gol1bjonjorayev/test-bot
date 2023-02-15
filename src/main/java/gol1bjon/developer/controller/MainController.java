package gol1bjon.developer.controller;

import gol1bjon.developer.container.ComponentContainer;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.entity.Customer;
import gol1bjon.developer.files.WorkWithFiles;
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

        if (message.hasText()) {
            String text = message.getText();
            handleText(user, message, text);
        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            handleContact(user, message, contact);
        }
    }

    private static void handleContact(User user, Message message, Contact contact) {
        String chatId = String.valueOf(message.getChatId());
    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Customer customer = CustomerService.getCustomerByChatId(chatId);

        if (customer == null) {
            CustomerService.addCustomer(chatId,new Contact());
        }

        if (text.equals("/start")) {
            sendMessage.setText("Assalomu alaykum!");
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        }


    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (data.startsWith("voting/")) {
            String[] split = data.split("/");
            String candidateId = split[1];

            Candidate candidate = CandidateService.getCandidateById(candidateId);
            Customer customer = CustomerService.getCustomerByChatId(chatId);

            if (!customer.isHasVoted()) {
                customer.setHasVoted(true);
                candidate.setCountVotes(candidate.getCountVotes() + 1);

                WorkWithFiles.writeCandidateList();
                WorkWithFiles.writeCustomerList();
                sendMessage.setText("siz" + candidate.getFullName() + " ga ovoz berdingiz.");
            } else {
                sendMessage.setText("siz avval ovoz bergansiz");
            }
            ComponentContainer.MY_BOT.sendMsg(sendMessage);

        }
    }


}
