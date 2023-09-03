package org.cleverbank.menu;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.menu.action.AccountMenuAction;

import java.util.List;
import java.util.Scanner;

public class AccountMenu extends AbstractMenu {

    private final static String ACCOUNT_MENU =
            """
                    1. Просмотреть информацию по всем счетам
                    2. Открыть новый счет
                    3. Закрыть счет (по фио владельца)
                    4. Корректировать данные счета (по фио владельца)
                    5. Вернуться в главное меню""";

    public static void start() {
        while (true) {
            printMenu(ACCOUNT_MENU);
            Scanner sc = new Scanner(System.in);
            AccountDAO accountDAO = new AccountDAO();
            TransactionDB transactionDB = new TransactionDB();
            transactionDB.initTransaction(accountDAO);
            switch (sc.nextInt()) {
                case 1 -> CallTransaction.doSelect(() -> {
                    List<Account> accounts = accountDAO.findAll();
                    for (Account account : accounts)
                        System.out.println(account.toString());
                }, transactionDB);
                case 2 -> CallTransaction.doTransaction(() ->
                        accountDAO.create(AccountMenuAction.create()), transactionDB);
                case 3 -> {
                    Account account = AccountMenuAction.enterUserFullName();
                    if (account == null) {
                        System.out.println("Такого пользователя не существует!");
                    } else {
                        CallTransaction.doTransaction(() ->
                                accountDAO.delete(account.getUser()), transactionDB);
                        System.out.println("Удаление произведено успешно");
                    }
                }
                case 4 -> {

                    Account accountUpdate = AccountMenuAction.enterUserFullName();
                    if (accountUpdate == null) {
                        System.out.println("Такого пользователя не существует!");
                    } else {
                        CallTransaction.doTransaction(() ->
                                accountDAO.update(AccountMenuAction.update(accountUpdate)), transactionDB);
                        System.out.println("Данные успешно изменены");
                    }
                }
                case 5 -> {
                    return;
                }
            }

        }
    }
}
