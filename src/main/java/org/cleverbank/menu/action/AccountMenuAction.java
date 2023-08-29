package org.cleverbank.menu.action;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.dao.BankDAO;
import org.cleverbank.dao.TypeCurrencyDAO;
import org.cleverbank.dao.UserDAO;
import org.cleverbank.entities.Account;
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
            id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
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

    public static Account enterUserFullName() {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.findEntityByUser(UserMenuAction.enterFullName());
    }

    public static Account update(Account account) {
        TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();
        System.out.println("изменить номер счета (ввести новый/N):");
        String numberAccount = scanner.nextLine();
        if (!numberAccount.equals("N"))
            account.setNumberAccount(numberAccount);
        System.out.println("изменить тип валюты (выбрать новую/N):");
                for (TypeCurrency type : typeCurrencyDAO.findAll()) {
            System.out.printf("%d - %s", type.getId(), type.getName());
        }
        String type = scanner.next();
        if (!type.equals("N"))
            account.setCurrency(typeCurrencyDAO.findEntityById(Integer.valueOf(type)));
        return account;
    }
}
