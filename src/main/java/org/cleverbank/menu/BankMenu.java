package org.cleverbank.menu;

import org.cleverbank.DAO.BankDAO;
import org.cleverbank.entities.Bank;
import org.cleverbank.menu.action.BankMenuAction;

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
                    bankDAO.create(BankMenuAction.create());
                    break;
                case 3:
                    id = bankDAO.findEntityByName(BankMenuAction.enterName());
                    if (id != 0) {
                        bankDAO.delete(id);
                    } else {
                        System.out.println("Нет такого банка!");
                    }
                    break;
                case 4:
                    id = bankDAO.findEntityByName(BankMenuAction.enterName());
                    if (id != 0) {
                        Bank bankUpdate = bankDAO.findEntityById(id);
                        bankUpdate = BankMenuAction.update(bankUpdate);
                        bankDAO.update(bankUpdate);
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
