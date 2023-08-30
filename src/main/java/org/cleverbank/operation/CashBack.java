package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class CashBack extends Thread {

    private volatile boolean mFinish = false;
    public static boolean getCashBank(double percent) {
        Boolean processOperation = false;
        LocalDate dateNow = LocalDate.now();
        LocalDate dateEndOfMonth = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
        if (dateNow.equals(dateEndOfMonth)) {
            AccountDAO accountDAO = new AccountDAO();
            ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.findAll();
            accounts = (ArrayList<Account>) accounts.stream()
                    .map(account -> {
                        double v = account.getRemainder() * (1 + percent / 100.0);
                        account.setRemainder(v);
                        return account;
                    })
                    .toList();
            processOperation = true;
        }
        return processOperation;
    }
}
