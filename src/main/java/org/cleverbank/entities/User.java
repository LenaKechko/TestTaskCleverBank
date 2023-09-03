package org.cleverbank.entities;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String lastName;
    private String name;
    private String middleName;
    private String address;
    private String phoneNumber;


    public User(String lastName, String name, String middleName) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ФИО: " + lastName + " " + name + " " + middleName +
                "\nАдрес: " + address +
                "\nНомер телефона: " + phoneNumber +
                "\n------------------------------------");
        return result.toString();
    }
}
