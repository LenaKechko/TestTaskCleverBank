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
import java.util.ArrayList;
import java.util.Scanner;

public class AccountMenuAction {
    private static Scanner scanner = new Scanner(System.in);

    public static Account create() {
        Account account = new Account();
        TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();
        while (true) {
            BankDAO bankDAO = new BankDAO();
            int id = bankDAO.findEntityByName(BankMenuAction.enterName());
            if (id != 0) {
                account.setBank(bankDAO.findEntityById(id));
                break;
            }
            else {
                System.out.println("Такого банка не существует, попробуйте еще раз");
            }
        }
        System.out.println("Выберите действия (укажите число):\n" +
                "1 - Счет открывается на существующего пользователя\n" +
                "2 - Создать нового пользователя");
        int temp = scanner.nextInt();
        while (true) {
            User user = null;
            UserDAO userDAO = new UserDAO();
            int id;
            if (temp == 1) {
                id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());

            } else {
                user = UserMenuAction.create();
                userDAO.create(user);
                id = userDAO.findEntityByFullName(user);
            }
            user = userDAO.findEntityById(id);
            if (user != null) {
                account.setUser(user);
                break;
            }
            System.out.println("Такого пользователя не существует, повторите ввод");
        }
        scanner.nextLine();
        System.out.println("Введите номер счета:");
        account.setNumberAccount(scanner.nextLine());
        System.out.println("Дата открытия счета:");
        account.setOpeningDate(Date.from(Instant.now()));
        System.out.println(account.getOpeningDate());
        System.out.println("Введите сумму:");
        account.setRemainder(scanner.nextDouble());
        System.out.println("Выберите тип валюты на счете (укажите число):");
        ArrayList<TypeCurrency> typeCurrencies = (ArrayList<TypeCurrency>) typeCurrencyDAO.findAll();
        for (TypeCurrency type : typeCurrencies) {
            System.out.printf("%d - %s\n", type.getId(), type.getName());
        }
        int id = scanner.nextInt();
        account.setCurrency(typeCurrencyDAO.findEntityById(id));
        return account;
    }

    public static Account enterUserFullName() {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.findEntityByUser(UserMenuAction.enterFullName());
    }

    public static Account update(Account account) {
        TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();
        System.out.println("Изменить номер счета (ввести новый/N):");
        String numberAccount = scanner.nextLine();
        if (!numberAccount.equals("N"))
            account.setNumberAccount(numberAccount);
        System.out.println("Изменить тип валюты (выбрать новую/N):");
        for (TypeCurrency type : typeCurrencyDAO.findAll()) {
            System.out.printf("%d - %s\n", type.getId(), type.getName());
        }
        String type = scanner.next();
        if (!type.equals("N"))
            account.setCurrency(typeCurrencyDAO.findEntityById(Integer.valueOf(type)));
        return account;
    }
}
