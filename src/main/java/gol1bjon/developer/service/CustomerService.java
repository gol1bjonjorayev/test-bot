package gol1bjon.developer.service;

import gol1bjon.developer.db.DataBase;
import gol1bjon.developer.entity.Customer;
import gol1bjon.developer.files.WorkWithFiles;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.Random;
import java.util.UUID;

public class CustomerService {
    public static Customer getCustomerByChatId(String chatId){
        DataBase Database;
        return DataBase.customerList.stream()
                .filter(customer -> customer.getChatId().equals(chatId))
                .findFirst().orElse(null);
    }

    public static Customer getCustomerByPhoneNumber(String phoneNumber){
        return DataBase.customerList.stream()
                .filter(customer -> customer.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElse(null);
    }

    public static Customer addCustomer(String chatId, Contact contact){
        if(getCustomerByChatId(chatId) != null) return null;
        if(getCustomerByPhoneNumber(contact.getPhoneNumber()) != null) return null;

        String code = String.valueOf(new Random().nextInt(1000, 10000));

        Customer customer = new Customer(chatId, contact.getFirstName(),
                contact.getLastName(), contact.getPhoneNumber(),code);
        DataBase.customerList.add(customer);
        WorkWithFiles.writeCustomerList();
        return customer;
    }
}
