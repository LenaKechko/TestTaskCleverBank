package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;

import java.util.List;

public class CashBack {

    public static boolean getCashBank(double percent) {
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(accountDAO);
        CallTransaction.doTransaction(() -> {
            List<Account> accounts = accountDAO.findAll();
            accounts.stream()
                    .map(account -> {
                        double v = account.getRemainder() * (1 + percent / 100.0);
                        account.setRemainder(v);
                        return account;
                    })
                    .forEach(accountDAO::updateRemainder);
        }, transactionDB);
        return true;
    }
}
