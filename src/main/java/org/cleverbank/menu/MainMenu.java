package org.cleverbank.menu;

import java.util.Scanner;

/**
 * Класс отвечающий за основное меню с выбором начальных операций
 *
 * @author Кечко Елена
 */
public class MainMenu extends AbstractMenu {
    /**
     * Константа вида главного меню
     */
    private final static String MAIN_MENU =
            """
                    1. Выполнение операций с аккаунтом
                    2. Операции с пользователями
                    3. Операции со счетами
                    4. Операции с банками
                    5. Завершить работу приложения""";

    /**
     * Метод позволяющий выбрать раздел операций
     */
    public static void start() {
        while (true) {
            printMenu(MAIN_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> OperationMenu.start();
                case 2 -> UserMenu.start();
                case 3 -> AccountMenu.start();
                case 4 -> BankMenu.start();
                case 5 -> {
                    return;
                }
            }
        }
    }
}
