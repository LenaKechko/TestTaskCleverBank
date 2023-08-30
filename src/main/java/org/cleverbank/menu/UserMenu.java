package org.cleverbank.menu;

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
        while (true) {
            printMenu(USER_MENU);
            Scanner sc = new Scanner(System.in);
            UserDAO userDAO = new UserDAO();
            Integer id;
            switch (sc.nextInt()) {
                case 1:
                    ArrayList<User> users = (ArrayList<User>) userDAO.findAll();
                    for (User user : users) {
                        System.out.println(user.toString());
                    }
                    break;
                case 2:
                    userDAO.create(UserMenuAction.create());
                    break;
                case 3:
                    id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                    if (id != 0) {
                        userDAO.delete(id);
                        System.out.println("Удаление произведено успешно");
                    } else {
                        System.out.println("Нет такого пользователя!");
                    }
                    break;
                case 4:
                    id = userDAO.findEntityByFullName(UserMenuAction.enterFullName());
                    if (id != 0) {
                        User userUpdate = userDAO.findEntityById(id);
                        userUpdate = UserMenuAction.update(userUpdate);
                        userDAO.update(userUpdate);
                        System.out.println("Данные успешно изменены");
                    } else {
                        System.out.println("Нет такого пользователя для изменения!");
                    }
                    break;
                case 5:
                    return;
            }
        }
    }
}
