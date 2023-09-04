package org.cleverbank.menu;

/**
 * Абстрактный класс с выводмо информации на экран
 *
 * @author Кечко Елена
 */

public abstract class AbstractMenu {

    /**
     * Метод выводить на консоль информацию
     *
     * @param menu информация для вывода
     */

    public static void printMenu(String menu) {
        System.out.println(menu);
    }

}
