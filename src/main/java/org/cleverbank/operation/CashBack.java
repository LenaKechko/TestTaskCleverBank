package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;

import java.util.ArrayList;

public class CashBack {

    public static boolean getCashBank(double percent) {
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.findAll();
        accounts.stream()
                .map(account -> {
                    double v = account.getRemainder() * (1 + percent / 100.0);
                    account.setRemainder(v);
                    return account;
                })
                .forEach(accountDAO::updateRemainder);
        return true;
    }
}
