package org.cleverbank.menu.action;

import org.cleverbank.entities.Bank;

import java.util.Scanner;

public class BankMenuAction {

    private static Scanner scanner = new Scanner(System.in);

    public static Bank create() {
        Bank bank = new Bank();
        System.out.println("Введите наименование банка:");
        bank.setName(scanner.nextLine());
        System.out.println("Введите адрес:");
        bank.setAddress(scanner.nextLine());
        return bank;
    }

    public static String enterName() {
        System.out.println("Введите наименование банка:");
        String name = scanner.nextLine();
        return name;
    }

    public static Bank update(Bank bank) {
        System.out.println("изменить наименование банка (ввести новое/N):");
        String name = scanner.nextLine();
        if (!name.equals("N"))
            bank.setName(name);
        System.out.println("изменить адрес (ввести новый/N):");
        String address = scanner.nextLine();
        if (!address.equals("N"))
            bank.setAddress(address);
        return bank;
    }
}
