package org.cleverbank.menu;

import java.util.Scanner;

public class MainMenu extends AbstractMenu {

    private final static String MAIN_MENU =
            "1. Выполнение операций с аккаунтом\n" +
                    "2. Операции с пользователями\n" +
                    "3. Операции со счетами\n" +
                    "4. Операции с банками\n" +
                    "5. Завершить работу приложения";

    public static void start() {
        while (true) {
            printMenu(MAIN_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    OperationMenu.start();
                    break;
                case 2:
                    UserMenu.start();
                    break;
                case 3:
                    AccountMenu.start();
                    break;
                case 4:
                    BankMenu.start();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}
