package org.cleverbank.menu;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.TypeTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.TypeTransactionEnum;
import org.cleverbank.operation.UserOperationWithAccount;

import java.math.BigDecimal;
import java.util.Locale;
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
        while (true) {
            printMenu(OPERATION_MENU);
            transactionDB.initTransaction(typeTransactionDAO, accountDAO);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println("Введите номер счета отправителя:");
                    final String numberAccountSender = scanner.nextLine();
                    System.out.println("Введите номер счета получателя:");
                    final String numberAccountReceiver = scanner.nextLine();
                    System.out.println("Введите сумму (формат XX,XX):");
                    final BigDecimal money = scanner.useLocale(Locale.ENGLISH).nextBigDecimal();
                    CallTransaction.doTransaction(() ->
                            userAction.runOperation(
                                    typeTransactionDAO.findEntityByType(TypeTransactionEnum.TRANSFER),
                                    accountDAO.findEntityByNumberAccount(numberAccountSender),
                                    accountDAO.findEntityByNumberAccount(numberAccountReceiver),
                                    money), transactionDB);
                }
                case 2 -> {
                    System.out.println("Введите номер счета, с которого снимаются средства:");
                    final String numberAccountReceiver = scanner.nextLine();
                    System.out.println("Введите сумму (формат XX,XX):");
                    final BigDecimal money = scanner.useLocale(Locale.ENGLISH).nextBigDecimal();
                    CallTransaction.doTransaction(() ->
                            userAction.runOperation(
                                    typeTransactionDAO.findEntityByType(TypeTransactionEnum.WITHDRAWAL),
                                    null,
                                    accountDAO.findEntityByNumberAccount(numberAccountReceiver),
                                    money), transactionDB);
                }
                case 3 -> {
                    System.out.println("Введите номер, пополняемого счета:");
                    final String numberAccountSender = scanner.nextLine();
                    System.out.println("Введите сумму (формат XX,XX):");
                    final BigDecimal money = scanner.useLocale(Locale.ENGLISH).nextBigDecimal();
                    CallTransaction.doTransaction(() ->
                            userAction.runOperation(
                                    typeTransactionDAO.findEntityByType(TypeTransactionEnum.REPLENISHMENT),
                                    accountDAO.findEntityByNumberAccount(numberAccountSender),
                                    null, money), transactionDB);
                }
                case 4 -> {
                    while (true) {
                        System.out.println("Введите номер счета:");
                        String numberAccount = scanner.nextLine();
                        Account account = CallTransaction.<Account>doSelect(() ->
                                accountDAO.findEntityByNumberAccount(numberAccount), transactionDB);
                        if (account == null) {
                            System.out.println("Не верно введен счет!");
                            continue;
                        }
                        AccountStatementMenu.start(account);
                        break;
                    }

                }
                case 5 -> {
                    return;
                }
            }
        }
    }

}
