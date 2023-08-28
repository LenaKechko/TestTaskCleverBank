package org.cleverbank.Menu;

import java.util.Scanner;

public class AccountMenu extends AbstractMenu {

    private final static String ACCOUNT_MENU =
            "1. Display account information\n" +
                    "2. Create new account\n" +
                    "3. Close account (by full name owner)\n" +
                    "4. Change account data (by full name owner)\n" +
                    "5. Return to main menu";

    public static void start() {
        while (true) {
            printMenu(ACCOUNT_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    break;
                case 2:
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
