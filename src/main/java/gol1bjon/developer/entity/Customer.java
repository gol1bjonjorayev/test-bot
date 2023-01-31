package gol1bjon.developer.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String chatId;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String hasVoted;
    private String confirmPassword;


    public Customer(String chatId, String firstName, String lastName, String phoneNumber, String confirmPassword) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.confirmPassword = confirmPassword;
    }

}
