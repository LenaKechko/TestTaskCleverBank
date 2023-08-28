package org.cleverbank.menu;

import org.cleverbank.DAO.AccountDAO;
import org.cleverbank.DAO.TypeCurrencyDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.TypeCurrency;
import org.cleverbank.menu.action.AccountMenuAction;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountMenu extends AbstractMenu {

    private final static String ACCOUNT_MENU =
            "1. Просмотреть информацию по всем счетам\n" +
                    "2. Открыть новый счет\n" +
                    "3. Закрыть счет (по фио владельца)\n" +
                    "4. Кореектировать данные счета (по фио владельца)\n" +
                    "5. Вернуться в главное меню";

    public static void start() {
        while (true) {
            printMenu(ACCOUNT_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            AccountDAO accountDAO = new AccountDAO();
            switch (sc.nextInt()) {
                case 1:
                    ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.findAll();
                    for (Account account : accounts)
                        System.out.println(account.toString());
                    break;
                case 2:
                    accountDAO.create(AccountMenuAction.create());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
            }
        }
    }
}
