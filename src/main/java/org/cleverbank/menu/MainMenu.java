package org.cleverbank.menu;

import java.util.Scanner;

public class MainMenu extends AbstractMenu {

    private final static String MAIN_MENU =
            "1. Execute account operation\n" +
                    "2. Operations with users\n" +
                    "3. Operations with account\n" +
                    "4. Operations with banks\n" +
                    "5. Close the application";

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
