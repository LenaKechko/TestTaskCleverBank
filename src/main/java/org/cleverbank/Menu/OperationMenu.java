package org.cleverbank.Menu;

import java.util.Scanner;

public class OperationMenu extends AbstractMenu {

    private final static String OPERATION_MENU =
            "1. Transfer money between two accounts\n" +
                    "2. Withdrawal money\n" +
                    "3. Account replenishment\n" +
                    "4. Return to main menu";

    public static void start() {
        while (true) {
            printMenu(OPERATION_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return;
            }
        }
    }

}
