package org.cleverbank.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Класс реализующий сущность счет Account, соответствует таблице БД accounts
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * Поле для уникального идентификатора
     */
    private int id;
    /**
     * Поле для номера аккаунта
     */
    private String numberAccount;
    /**
     * Поле для даты открытия счета
     */
    private Date openingDate;
    /**
     * Поле для остатка на счету
     */
    private BigDecimal remainder;
    /**
     * Поле для сущности клиента, служит для реализации связи
     */
    private User user;
    /**
     * Поле для сущности банка, служит для реализации связи
     */
    private Bank bank;
    /**
     * Поле для сущности валюты, служит для реализации связи
     */
    private TypeCurrency currency;

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
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
