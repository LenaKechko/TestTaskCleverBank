package org.cleverbank.menu.action;

import org.cleverbank.entities.User;

import java.util.Scanner;

/**
 * Класс с операциями необходимыми в меню операций над пользователем
 *
 * @author Кечко Елена
 */
public class UserMenuAction {
    /**
     * Поле, ппредоставляющее возможность считывать данные с консоли
     */
    private final static Scanner scanner = new Scanner(System.in);

    /**
     * Метод для ввода данных необходимых для создания пользователя
     *
     * @return объект пользователь User
     */
    public static User create() {
        User user = new User();
        System.out.println("Введите фамилию:");
        user.setLastName(scanner.next());
        System.out.println("Введите имя:");
        user.setName(scanner.next());
        System.out.println("Введите отчество:");
        user.setMiddleName(scanner.next());
        scanner.nextLine();
        System.out.println("Введите адрес:");
        user.setAddress(scanner.nextLine());
        System.out.println("Введите номер телефона:");
        user.setPhoneNumber(scanner.nextLine());
        return user;
    }

    /**
     * Метод для ввода фио пользователя
     *
     * @return объект пользователя User
     */
    public static User enterFullName() {
        System.out.println("Введите фамилию:");
        String lastName = scanner.next();
        System.out.println("Введите имя:");
        String name = scanner.next();
        System.out.println("Введите отчество:");
        String middleName = scanner.next();
        User user = new User(lastName, name, middleName);
        return user;
    }

    /**
     * Метод для ввода данных на изменение объекта User
     *
     * @param user объект для изменения
     * @return объект пользователя User
     */
    public static User update(User user) {
        System.out.println("Изменить фамилию (ввести новую/N):");
        String lastName = scanner.next();
        if (!lastName.equals("N"))
            user.setLastName(lastName);
        System.out.println("Изменить имя (ввести новое/N):");
        String name = scanner.next();
        if (!name.equals("N"))
            user.setName(name);
        System.out.println("Изменить отчество (ввести новое/N):");
        String middleName = scanner.next();
        if (!middleName.equals("N"))
            user.setMiddleName(middleName);
        scanner.nextLine();
        System.out.println("Изменить адрес (ввести новый/N):");
        String address = scanner.nextLine();
        if (!address.equals("N"))
            user.setAddress(address);
        System.out.println("Изменить номер телефона (ввести новый/N):");
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.equals("N"))
            user.setLastName(phoneNumber);
        return user;
    }
}
