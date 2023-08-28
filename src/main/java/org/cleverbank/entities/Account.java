package org.cleverbank.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private int id;
    private String numberAccount;
    private Date openingDate;
    private Double remainder;
    private User user;
    private Bank bank;
    private TypeCurrency currency;
}
