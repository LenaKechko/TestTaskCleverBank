package org.cleverbank.menu;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.TypeTransactionDAO;
import org.cleverbank.entities.TypeTransactionEnum;
import org.cleverbank.operation.UserOperationWithAccount;

import java.util.Scanner;

public class OperationMenu extends AbstractMenu {

    private final static String OPERATION_MENU =
            """
                    1. Перевод денежных средств на другой аккаунт
                    2. Снятие средств
                    3. Пополнение счета
                    4. Запросить выписку за период
                    5. Вернуться в основное меню""";

    public static void start() {
        UserOperationWithAccount userAction = new UserOperationWithAccount();
        TypeTransactionDAO typeTransactionDAO = new TypeTransactionDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        String numberAccountReceiver, numberAccountSender;
        Double money;
        while (true) {
            printMenu(OPERATION_MENU);
            transactionDB.initTransaction(typeTransactionDAO, accountDAO);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> {
                    try {
                        System.out.println("Введите номер счета отправителя:");
                        numberAccountSender = scanner.nextLine();
                        System.out.println("Введите номер счета получателя:");
                        numberAccountReceiver = scanner.nextLine();
                        System.out.println("Введите сумму:");
                        money = scanner.nextDouble();
                        userAction.runOperation(
                                typeTransactionDAO.findEntityByType(TypeTransactionEnum.TRANSFER),
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
                }
                case 2 -> {
                    try {
                        System.out.println("Введите номер счета, с которого снимаются средства:");
                        numberAccountReceiver = scanner.nextLine();
                        System.out.println("Введите сумму:");
                        money = scanner.nextDouble();

                        userAction.runOperation(
                                typeTransactionDAO.findEntityByType(TypeTransactionEnum.WITHDRAWAL),
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
                }
                case 3 -> {
                    try {
                        System.out.println("Введите номер, пополняемого счета:");
                        numberAccountSender = scanner.nextLine();
                        System.out.println("Введите сумму:");
                        money = scanner.nextDouble();

                        userAction.runOperation(
                                typeTransactionDAO.findEntityByType(TypeTransactionEnum.REPLENISHMENT),
                                accountDAO.findEntityByNumberAccount(numberAccountSender),
                                null, money);
                        transactionDB.commit();
                    } catch (Exception e) {
                        transactionDB.rollback();
                        e.printStackTrace();
                    } finally {
                        transactionDB.endTransaction();
                    }
                }
                case 4 -> {
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
                }
                case 5 -> {
                    return;
                }
            }
        }
    }

}
