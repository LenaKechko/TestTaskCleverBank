package org.cleverbank.menu;

import org.cleverbank.entities.Account;
import org.cleverbank.operation.AccountStatement;
import org.cleverbank.operation.MoneyStatement;

import java.util.Scanner;

public class StatementMenu extends AbstractMenu {
    private final static String STATEMENT_MENU =
            """
                    1. Выписка за период по всем операциям
                    2. Выписка потраченных и полученных средств за период времени
                    3. Вернуться в основное меню""";

    public static void start(Account account) {
        while (true) {
            printMenu(STATEMENT_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> AccountStatementMenu.start(account, new AccountStatement());
                case 2 -> AccountStatementMenu.start(account, new MoneyStatement());
                case 3 -> {
                    return;
                }
            }
        }
    }
}
