package org.cleverbank.menu;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.dao.TypeTransactionDAO;
import org.cleverbank.entities.TypeTransaction;
import org.cleverbank.operation.UserOperationWithAccount;

import java.util.Scanner;

public class OperationMenu extends AbstractMenu {

    private final static String OPERATION_MENU =
            "1. Перевод денежных средств на другой аккаунт\n" +
                    "2. Снятие средств\n" +
                    "3. Пополнение счета\n" +
                    "4. Вернуться в основное меню";

    public static void start() {
        UserOperationWithAccount userAction = new UserOperationWithAccount();
        TypeTransactionDAO typeTransactionDAO = new TypeTransactionDAO();
        AccountDAO accountDAO = new AccountDAO();
        String numberAccountReceiver, numberAccountSender;
        Double money;
        while (true) {
            printMenu(OPERATION_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    System.out.println("Введите номер счета отправителя:");
                    numberAccountSender = scanner.nextLine();
                    System.out.println("Введите номер счета получателя:");
                    numberAccountReceiver = scanner.nextLine();
                    System.out.println("Введите сумму:");
                    money = scanner.nextDouble();
                    userAction.runOperation(
                            typeTransactionDAO.findEntityByType("Перевод"),
                            accountDAO.findEntityByNumberAccount(numberAccountSender),
                            accountDAO.findEntityByNumberAccount(numberAccountReceiver),
                            money
                    );
                    break;
                case 2:
                    System.out.println("Введите номер счета, с которого снимаются средства:");
                    numberAccountReceiver = scanner.nextLine();
                    System.out.println("Введите сумму:");
                    money = scanner.nextDouble();

                    userAction.runOperation(
                            typeTransactionDAO.findEntityByType("Снятие средств"),
                            null,
                            accountDAO.findEntityByNumberAccount(numberAccountReceiver),
                            money
                    );
                    break;
                case 3:
                    System.out.println("Введите номер, пополняемого счета:");
                    numberAccountSender = scanner.nextLine();
                    System.out.println("Введите сумму:");
                    money = scanner.nextDouble();

                    userAction.runOperation(
                            typeTransactionDAO.findEntityByType("Пополнение счета"),
                            accountDAO.findEntityByNumberAccount(numberAccountSender),
                            null, money
                    );
                    break;
                case 4:
                    return;
            }
        }
    }

}
