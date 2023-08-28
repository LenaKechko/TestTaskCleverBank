package org.cleverbank.Menu;

import org.cleverbank.DAO.BankDAO;
import org.cleverbank.DAO.UserDAO;
import org.cleverbank.entities.Bank;
import org.cleverbank.entities.User;

import java.util.ArrayList;
import java.util.Scanner;

public class BankMenu extends AbstractMenu {
    private final static String BANK_MENU =
            "1. Display information about banks\n" +
                    "2. Add new bank\n" +
                    "3. Delete bank (by name)\n" +
                    "4. Change bank date (by name)\n" +
                    "5. Return to main menu";

    public static void start() {
        while (true) {
            printMenu(BANK_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            BankDAO bankDAO = new BankDAO();
            String name, middleName;
            Integer id;
            switch (sc.nextInt()) {
                case 1:
                    ArrayList<Bank> banks = (ArrayList<Bank>) bankDAO.findAll();
                    for (Bank bank : banks) {
                        System.out.println(bank.toString());
                    }
                    break;
                case 2:
                    Bank bank = new Bank();
                    System.out.println("Введите наименование банка:");
                    bank.setName(scanner.next());
                    System.out.println("Введите адрес:");
                    bank.setAddress(scanner.next());
                    bankDAO.create(bank);
                    break;
                case 3:
                    System.out.println("Введите наименование банка:");
                    name = scanner.next();
                    id = bankDAO.findEntityByName(name);
                    if (id != 0) {
                        bankDAO.delete(id);
                    } else {
                        System.out.println("Нет такого банка!");
                    }
                    break;
                case 4:
                    System.out.println("Введите наименование банка:");
                    name = scanner.next();
                    id = bankDAO.findEntityByName(name);
                    if (id != 0) {
                        bank = bankDAO.findEntityById(id);
                        System.out.println("изменить наименование банка (ввести новое/N):");
                        name = scanner.next();
                        if (!name.equals("N"))
                            bank.setName(name);
                        System.out.println("изменить адрес (ввести новый/N):");
                        String address = scanner.next();
                        if (!address.equals("N"))
                            bank.setAddress(address);
                        bankDAO.update(bank);
                    } else {
                        System.out.println("Банк для изменений не найден!");
                    }
                    break;
                case 5:
                    return;
            }
        }
    }
}
