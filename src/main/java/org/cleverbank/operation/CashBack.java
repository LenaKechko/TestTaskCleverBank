package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CashBack implements Runnable {
    private final double percent;
    private static boolean isOperationDone = false;

    public CashBack(double percent) {
        this.percent = percent;
    }

    @Override
    public synchronized void run() {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateEndOfMonth = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
        if (!isOperationDone && dateNow.equals(dateEndOfMonth)) {
            isOperationDone = getCashBank(percent);
        }
        if (isOperationDone && !dateNow.equals(dateEndOfMonth)) {
            isOperationDone = false;
        }
    }

    public static boolean getCashBank(double percent) {
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(accountDAO);
        CallTransaction.doTransaction(() -> {
            List<Account> accounts = accountDAO.findAll();
            accounts.stream()
                    .map(account -> {
                        BigDecimal v = account.getRemainder().multiply(
                                BigDecimal.valueOf((1 + percent / 100.0)));
                        account.setRemainder(v);
                        return account;
                    })
                    .forEach(accountDAO::updateRemainder);
        }, transactionDB);
        return true;
    }
}
