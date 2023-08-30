package org.cleverbank.menu;

import org.cleverbank.dao.BankDAO;
import org.cleverbank.entities.Bank;
import org.cleverbank.menu.action.BankMenuAction;

import java.util.ArrayList;
import java.util.Scanner;

public class BankMenu extends AbstractMenu {
    private final static String BANK_MENU =
            "1. Вывести информацию о банках\n" +
                    "2. Добавить новый банк\n" +
                    "3. Удалить банк (по наименованию)\n" +
                    "4. Изменить данные банка (по наименованию)\n" +
                    "5. Вернуться в главное меню";

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
                        System.out.println("Удаление произведено успешно");
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
                        System.out.println("Данные успешно изменены");
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
