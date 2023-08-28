package org.cleverbank.menu.action;

import org.cleverbank.DAO.BankDAO;
import org.cleverbank.DAO.TypeCurrencyDAO;
import org.cleverbank.DAO.UserDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.Bank;
import org.cleverbank.entities.TypeCurrency;
import org.cleverbank.entities.User;

import java.sql.Date;
import java.time.Instant;
import java.util.Scanner;

public class AccountMenuAction {
    private static Scanner scanner = new Scanner(System.in);

    public static Account create() {
        Account account = new Account();
        TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();
        System.out.println("Выберите банк:");

        BankDAO bankDAO = new BankDAO();
        int id = bankDAO.findEntityByName(BankMenuAction.enterName());
        account.setBank(bankDAO.findEntityById(id));
        System.out.println("Выберите действие (укажите число):\n" +
                "1 - Счет открывается на существующего пользователя\n" +
                "2 - Создать нового пользователя");
        int temp = scanner.nextInt();
        User user = null;
        if (temp == 1) {
            UserDAO userDAO = new UserDAO();
            int id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
            user = userDAO.findEntityById(id);
        } else {
            user = UserMenuAction.create();
        }
        account.setUser(user);

        System.out.println("Введите номер счета:");
        account.setNumberAccount(scanner.nextLine());
        System.out.println("Дата открытия счета:");
        account.setOpeningDate(Date.from(Instant.now()));
        System.out.println(account.getOpeningDate());
        System.out.println("Введите сумму:");
        account.setRemainder(scanner.nextDouble());
        System.out.println("Выберите тип валюты на счете (укажите число):");
        for (TypeCurrency type : typeCurrencyDAO.findAll()) {
            System.out.printf("%d - %s", type.getId(), type.getName());
        }
        id = scanner.nextInt();
        account.setCurrency(typeCurrencyDAO.findEntityById(id));
        return account;
    }
}
