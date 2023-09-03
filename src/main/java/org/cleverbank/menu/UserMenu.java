package org.cleverbank.menu;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.UserDAO;
import org.cleverbank.entities.User;
import org.cleverbank.menu.action.UserMenuAction;

import java.util.List;
import java.util.Scanner;


public class UserMenu extends AbstractMenu {

    private final static String USER_MENU =
            """
                    1. Вывести информацию о клиентах
                    2. Добавить нового клиента
                    3. Удалить клиента (по фио)
                    4. Изменить данные клиента (по фио)
                    5. Вернуться в главное меню""";

    public static void start() {
        while (true) {
            printMenu(USER_MENU);
            Scanner sc = new Scanner(System.in);
            UserDAO userDAO = new UserDAO();
            TransactionDB transactionDB = new TransactionDB();
            transactionDB.initTransaction(userDAO);
            switch (sc.nextInt()) {
                case 1 -> CallTransaction.doSelect(() -> {
                    List<User> users = userDAO.findAll();
                    for (User user : users) {
                        System.out.println(user.toString());
                    }
                }, transactionDB);
                case 2 -> CallTransaction.doTransaction(() ->
                        userDAO.create(UserMenuAction.create()), transactionDB);
                case 3 -> CallTransaction.doTransaction(() -> {
                    int id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                    if (id != 0) {
                        userDAO.delete(id);
                        System.out.println("Удаление произведено успешно");
                    } else {
                        System.out.println("Нет такого пользователя!");
                    }
                }, transactionDB);
                case 4 -> CallTransaction.doTransaction(() -> {
                    int id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                    if (id != 0) {
                        User userUpdate = userDAO.findEntityById(id);
                        userUpdate = UserMenuAction.update(userUpdate);
                        userDAO.update(userUpdate);
                        System.out.println("Данные успешно изменены");
                    } else {
                        System.out.println("Нет такого пользователя для изменения!");
                    }
                }, transactionDB);
                case 5 -> {
                    return;
                }
            }
        }
    }


}
