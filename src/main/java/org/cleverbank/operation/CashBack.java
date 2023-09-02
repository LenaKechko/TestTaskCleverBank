package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;

import java.util.ArrayList;

public class CashBack {

    public static boolean getCashBank(double percent) {
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(accountDAO);
        try {
            ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.findAll();
            accounts.stream()
                    .map(account -> {
                        double v = account.getRemainder() * (1 + percent / 100.0);
                        account.setRemainder(v);
                        return account;
                    })
                    .forEach(accountDAO::updateRemainder);
            transactionDB.commit();
        } catch (Exception e) {
            transactionDB.rollback();
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
        return true;
    }
}
