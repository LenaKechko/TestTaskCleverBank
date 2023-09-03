package org.cleverbank.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int id;
    private String numberAccount;
    private Date openingDate;
    private BigDecimal remainder;
    private User user;
    private Bank bank;
    private TypeCurrency currency;

    @Override
    public String toString() {
        return "Номер счета: " + numberAccount +
                "\nДата открытия счета: " + openingDate +
                "\n-------Клиент-------\n" + user.toString() +
                "\n--------Банк--------\n" + bank.toString() +
                "\nОстаток: " + remainder + " " + currency.getName() +
                "\n----------------------------------------------------";
    }
}
