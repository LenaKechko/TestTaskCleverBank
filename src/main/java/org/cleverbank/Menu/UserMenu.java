package org.cleverbank.Menu;

import org.cleverbank.DAO.UserDAO;
import org.cleverbank.entities.User;
import org.cleverbank.Menu.action.UserMenuAction;

import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu extends AbstractMenu {

    private final static String USER_MENU =
            "1. Display users data\n" +
                    "2. Add new user\n" +
                    "3. Delete user (by full name)\n" +
                    "4. Change user data (by full name)\n" +
                    "5. Return to main menu";


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
