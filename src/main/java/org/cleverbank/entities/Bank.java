package org.cleverbank.entities;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    private int id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "Наименование банка: " + name +
                "\nАдрес банка: " + address +
                "\n--------------------------------";
    }
}
