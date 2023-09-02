package org.cleverbank.menu;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.dao.TransactionDB;
import org.cleverbank.menu.action.AccountMenuAction;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountMenu extends AbstractMenu {

    private final static String ACCOUNT_MENU =
            "1. Просмотреть информацию по всем счетам\n" +
                    "2. Открыть новый счет\n" +
                    "3. Закрыть счет (по фио владельца)\n" +
                    "4. Корректировать данные счета (по фио владельца)\n" +
                    "5. Вернуться в главное меню";

    public static void start() {
        while (true) {
            printMenu(ACCOUNT_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            AccountDAO accountDAO = new AccountDAO();
            TransactionDB transactionDB = new TransactionDB();
            transactionDB.initTransaction(accountDAO);
            switch (sc.nextInt()) {
                case 1:
                    try {
                        ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.findAll();
                        transactionDB.commit();
                        for (Account account : accounts)
                            System.out.println(account.toString());
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 2:
                    try {
                        accountDAO.create(AccountMenuAction.create());
                        transactionDB.commit();
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 3:
                    try {
                        Account account = AccountMenuAction.enterUserFullName();
                        if (account == null) {
                            System.out.println("Такого пользователя не существует!");
                        } else {
                            accountDAO.delete(account);
                            transactionDB.commit();
                            System.out.println("Удаление произведено успешно");
                        }
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 4:
                    try {
                        Account accountUpdate = AccountMenuAction.enterUserFullName();
                        if (accountUpdate == null) {
                            System.out.println("Такого пользователя не существует!");
                        } else {
                            System.out.println(accountUpdate);
                            accountUpdate = AccountMenuAction.update(accountUpdate);
                            accountDAO.update(accountUpdate);
                            transactionDB.commit();
                            System.out.println("Данные успешно изменены");
                        }
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 5:
                    return;
            }

        }
    }
}
