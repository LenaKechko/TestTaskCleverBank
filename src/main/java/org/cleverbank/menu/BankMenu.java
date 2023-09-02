package org.cleverbank.menu;

import org.cleverbank.dao.BankDAO;
import org.cleverbank.connection.TransactionDB;
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
        TransactionDB transactionDB = new TransactionDB();
        while (true) {
            printMenu(BANK_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            BankDAO bankDAO = new BankDAO();
            transactionDB.initTransaction(bankDAO);
            String name, middleName;
            Integer id;
            switch (sc.nextInt()) {
                case 1:
                    try {
                        ArrayList<Bank> banks = (ArrayList<Bank>) bankDAO.findAll();
                        for (Bank bank : banks) {
                            System.out.println(bank.toString());
                        }
                        transactionDB.commit();
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 2:
                    try {
                        bankDAO.create(BankMenuAction.create());
                        transactionDB.commit();
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 3:
                    try {
                        id = bankDAO.findEntityByName(BankMenuAction.enterName());
                        transactionDB.commit();
                        if (id != 0) {
                            bankDAO.delete(id);
                            transactionDB.commit();
                            System.out.println("Удаление произведено успешно");
                        } else {
                            System.out.println("Нет такого банка!");
                        }
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 4:
                    try {
                        id = bankDAO.findEntityByName(BankMenuAction.enterName());
                        transactionDB.commit();
                        if (id != 0) {
                            Bank bankUpdate = bankDAO.findEntityById(id);
                            bankUpdate = BankMenuAction.update(bankUpdate);
                            bankDAO.update(bankUpdate);
                            transactionDB.commit();
                            System.out.println("Данные успешно изменены");
                        } else {
                            System.out.println("Банк для изменений не найден!");
                        }
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 5:
                    return;
            }
        }
    }
}
