package org.cleverbank.menu;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.BankDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Bank;
import org.cleverbank.menu.action.BankMenuAction;

import java.util.List;
import java.util.Scanner;

/**
 * Класс отвечающий за меню работы с операциями над списком банков
 *
 * @author Кечко Елена
 */
public class BankMenu extends AbstractMenu {
    /**
     * Константа вида меню операций над банками
     */
    private final static String BANK_MENU =
            """
                    1. Вывести информацию о банках
                    2. Добавить новый банк
                    3. Удалить банк (по наименованию)
                    4. Изменить данные банка (по наименованию)
                    5. Вернуться в главное меню""";

    /**
     * Метод позволяющий выбрать раздел операций над банками
     * Здесь представлены основные crud-операции: создание, удаление,
     * изменение и вывод на экран существующие банки
     */
    public static void start() {
        TransactionDB transactionDB = new TransactionDB();
        while (true) {
            printMenu(BANK_MENU);
            Scanner sc = new Scanner(System.in);
            BankDAO bankDAO = new BankDAO();
            transactionDB.initTransaction(bankDAO);

            switch (sc.nextInt()) {
                case 1 -> CallTransaction.doSelect(() -> {
                    List<Bank> banks = bankDAO.findAll();
                    for (Bank bank : banks) {
                        System.out.println(bank.toString());
                    }
                }, transactionDB);
                case 2 -> CallTransaction.doTransaction(() -> bankDAO.create(BankMenuAction.create()),
                        transactionDB);
                case 3 -> CallTransaction.doTransaction(() -> {
                    int id = bankDAO.findEntityByName(BankMenuAction.enterName());
                    if (id != 0) {
                        bankDAO.delete(id);
                        System.out.println("Удаление произведено успешно");
                    } else {
                        System.out.println("Нет такого банка!");
                    }
                }, transactionDB);
                case 4 -> CallTransaction.doTransaction(() -> {
                    int id = bankDAO.findEntityByName(BankMenuAction.enterName());
                    if (id != 0) {
                        Bank bankUpdate = bankDAO.findEntityById(id);
                        bankUpdate = BankMenuAction.update(bankUpdate);
                        bankDAO.update(bankUpdate);
                        System.out.println("Данные успешно изменены");
                    } else {
                        System.out.println("Банк для изменений не найден!");
                    }
                }, transactionDB);
                case 5 -> {
                    return;
                }
            }
        }
    }
}
