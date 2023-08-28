package org.cleverbank.Menu;

import org.cleverbank.DAO.UserDAO;
import org.cleverbank.entities.User;

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
            Scanner scanner = new Scanner(System.in);
            UserDAO userDAO = new UserDAO();
            String lastName, name, middleName;
            Integer id;
            switch (sc.nextInt()) {
                case 1:
                    ArrayList<User> users = (ArrayList<User>) userDAO.findAll();
                    for (User user : users) {
                        System.out.println(user.toString());
                    }
                    break;
                case 2:
                    User user = new User();
                    System.out.println("Введите фамилию:");
                    user.setLastName(scanner.next());
                    System.out.println("Введите имя:");
                    user.setName(scanner.next());
                    System.out.println("Введите отчество:");
                    user.setMiddleName(scanner.next());
                    System.out.println("Введите адрес:");
                    user.setAddress(scanner.next());
                    System.out.println("Введите номер телефона:");
                    user.setPhoneNumber(scanner.next());
                    userDAO.create(user);
                    break;
                case 3:
                    System.out.println("Введите фамилию:");
                    lastName = scanner.next();
                    System.out.println("Введите имя:");
                    name = scanner.next();
                    System.out.println("Введите отчество:");
                    middleName = scanner.next();
                    id = userDAO.findEntityByFullName(lastName, name, middleName);
                    if (id != 0) {
                        userDAO.delete(id);
                    } else {
                        System.out.println("Нет такого пользователя!");
                    }
                    break;
                case 4:
                    System.out.println("Введите фамилию:");
                    lastName = scanner.next();
                    System.out.println("Введите имя:");
                    name = scanner.next();
                    System.out.println("Введите отчество:");
                    middleName = scanner.next();
                    id = userDAO.findEntityByFullName(lastName, name, middleName);
                    if (id != 0) {
                        user = userDAO.findEntityById(id);
                        System.out.println("изменить фамилию (ввести новую/N):");
                        lastName = scanner.next();
                        if (!lastName.equals("N"))
                            user.setLastName(lastName);
                        System.out.println("изменить имя (ввести новое/N):");
                        name = scanner.next();
                        if (!name.equals("N"))
                            user.setName(name);
                        System.out.println("изменить отчество (ввести новое/N):");
                        middleName = scanner.next();
                        if (!middleName.equals("N"))
                            user.setMiddleName(middleName);
                        System.out.println("изменить адрес (ввести новый/N):");
                        String address = scanner.next();
                        if (!address.equals("N"))
                            user.setAddress(address);
                        System.out.println("изменить номер телефона (ввести новый/N):");
                        String phoneNumber = scanner.next();
                        if (!phoneNumber.equals("N"))
                            user.setLastName(phoneNumber);
                        userDAO.update(user);
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
