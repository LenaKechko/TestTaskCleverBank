package org.cleverbank.menu;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.dao.TransactionDB;
import org.cleverbank.dao.TypeTransactionDAO;
import org.cleverbank.operation.UserOperationWithAccount;

import java.util.Scanner;

public class OperationMenu extends AbstractMenu {

    private final static String OPERATION_MENU =
            "1. Перевод денежных средств на другой аккаунт\n" +
                    "2. Снятие средств\n" +
                    "3. Пополнение счета\n" +
                    "4. Запросить выписку за период\n" +
                    "5. Вернуться в основное меню";

    public static void start() {
        UserOperationWithAccount userAction = new UserOperationWithAccount();
        TypeTransactionDAO typeTransactionDAO = new TypeTransactionDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(typeTransactionDAO, accountDAO);
        String numberAccountReceiver, numberAccountSender;
        Double money;
        while (true) {
            printMenu(OPERATION_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    try {
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
                                money);
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
                        System.out.println("Введите номер счета, с которого снимаются средства:");
                        numberAccountReceiver = scanner.nextLine();
                        System.out.println("Введите сумму:");
                        money = scanner.nextDouble();

                        userAction.runOperation(
                                typeTransactionDAO.findEntityByType("Снятие средств"),
                                null,
                                accountDAO.findEntityByNumberAccount(numberAccountReceiver),
                                money);
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
                        System.out.println("Введите номер, пополняемого счета:");
                        numberAccountSender = scanner.nextLine();
                        System.out.println("Введите сумму:");
                        money = scanner.nextDouble();

                        userAction.runOperation(
                                typeTransactionDAO.findEntityByType("Пополнение счета"),
                                accountDAO.findEntityByNumberAccount(numberAccountSender),
                                null, money);
                        transactionDB.commit();
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                    break;
                case 4:
                    try {
                        while (true) {
                            System.out.println("Введите номер счета:");
                            String numberAccount = scanner.nextLine();
                            if (accountDAO.findEntityByNumberAccount(numberAccount) == null) {
                                System.out.println("Не верно введен счет!");
                                continue;
                            }
                            AccountStatementMenu.start(accountDAO.findEntityByNumberAccount(numberAccount));
                            transactionDB.commit();
                            break;
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
