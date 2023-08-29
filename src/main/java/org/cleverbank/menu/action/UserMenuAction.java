package org.cleverbank.menu.action;

import org.cleverbank.entities.User;

import java.util.Scanner;

public class UserMenuAction {

    private static Scanner scanner = new Scanner(System.in);
    public static User create() {
        User user = new User();
        System.out.println("Введите фамилию:");
        user.setLastName(scanner.next());
        System.out.println("Введите имя:");
        user.setName(scanner.next());
        System.out.println("Введите отчество:");
        user.setMiddleName(scanner.next());
        System.out.println("Введите адрес:");
        user.setAddress(scanner.nextLine());
        System.out.println("Введите номер телефона:");
        user.setPhoneNumber(scanner.nextLine());
        return user;
    }

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

    public static User update(User user) {
        System.out.println("изменить фамилию (ввести новую/N):");
        String lastName = scanner.next();
        if (!lastName.equals("N"))
            user.setLastName(lastName);
        System.out.println("изменить имя (ввести новое/N):");
        String name = scanner.next();
        if (!name.equals("N"))
            user.setName(name);
        System.out.println("изменить отчество (ввести новое/N):");
        String middleName = scanner.next();
        if (!middleName.equals("N"))
            user.setMiddleName(middleName);
        System.out.println("изменить адрес (ввести новый/N):");
        String address = scanner.nextLine();
        if (!address.equals("N"))
            user.setAddress(address);
        System.out.println("изменить номер телефона (ввести новый/N):");
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.equals("N"))
            user.setLastName(phoneNumber);
        return user;
    }
}
