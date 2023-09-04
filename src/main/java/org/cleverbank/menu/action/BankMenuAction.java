package org.cleverbank.menu.action;

import org.cleverbank.entities.Bank;

import java.util.Scanner;

/**
 * Класс с операциями необходимыми в меню операций над банками
 *
 * @author Кечко Елена
 */
public class BankMenuAction {
    /**
     * Поле, ппредоставляющее возможность считывать данные с консоли
     */
    private final static Scanner scanner = new Scanner(System.in);

    /**
     * Метод для ввода данных необходимых для создания банка
     *
     * @return объект пользователь Bank
     */
    public static Bank create() {
        Bank bank = new Bank();
        System.out.println("введите наименование банка:");
        bank.setName(scanner.nextLine());
        System.out.println("Введите адрес:");
        bank.setAddress(scanner.nextLine());
        return bank;
    }

    /**
     * Метод для ввода наименования банка
     *
     * @return наименование банка
     */
    public static String enterName() {
        System.out.println("Введите наименование банка:");
        String name = scanner.nextLine();
        return name;
    }

    /**
     * Метод для ввода данных на изменение объекта Bank
     *
     * @param bank объект для изменения
     * @return объект пользователя Bank
     */
    public static Bank update(Bank bank) {
        System.out.println("Изменить наименование банка (ввести новое/N):");
        String name = scanner.nextLine();
        if (!name.equals("N"))
            bank.setName(name);
        System.out.println("Изменить адрес (ввести новый/N):");
        String address = scanner.nextLine();
        if (!address.equals("N"))
            bank.setAddress(address);
        return bank;
    }
}
