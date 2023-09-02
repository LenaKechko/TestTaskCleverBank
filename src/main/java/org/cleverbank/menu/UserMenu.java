package org.cleverbank.menu;

import org.cleverbank.dao.TransactionDB;
import org.cleverbank.dao.UserDAO;
import org.cleverbank.entities.User;
import org.cleverbank.menu.action.UserMenuAction;

import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu extends AbstractMenu {

    private final static String USER_MENU =
            "1. Вывести информацию о клиентах\n" +
                    "2. Добавить нового клиента\n" +
                    "3. Удалить клиента (по фио)\n" +
                    "4. Изменить данные клиента (по фио)\n" +
                    "5. Вернуться в главное меню";


    public static void start() {
        TransactionDB transactionDB = new TransactionDB();
        while (true) {
            printMenu(USER_MENU);
            Scanner sc = new Scanner(System.in);
            UserDAO userDAO = new UserDAO();
            transactionDB.initTransaction(userDAO);
            Integer id;
            switch (sc.nextInt()) {
                case 1:
                    try {
                        ArrayList<User> users = (ArrayList<User>) userDAO.findAll();
                        for (User user : users) {
                            System.out.println(user.toString());
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
                        userDAO.create(UserMenuAction.create());
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
                        id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                        transactionDB.commit();
                        if (id != 0) {
                            userDAO.delete(id);
                            transactionDB.commit();
                            System.out.println("Удаление произведено успешно");
                        } else {
                            System.out.println("Нет такого пользователя!");
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
                        id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                        transactionDB.commit();
                        if (id != 0) {
                            User userUpdate = userDAO.findEntityById(id);
                            userUpdate = UserMenuAction.update(userUpdate);
                            userDAO.update(userUpdate);
                            transactionDB.commit();
                            System.out.println("Данные успешно изменены");
                        } else {
                            System.out.println("Нет такого пользователя для изменения!");
                        }
                    } catch (Exception e) {
                        transactionDB.rollback();
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
