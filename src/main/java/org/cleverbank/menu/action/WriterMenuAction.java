package org.cleverbank.menu.action;

import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.operation.AccountStatement;

import java.time.LocalDate;
import java.util.ArrayList;

public class WriterMenuAction {

    public static String getStatement(Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        ArrayList<BankTransaction> bankTransactions =
                bankTransactionDAO.findEntityByDate(dateStartPeriod, dateEndPeriod, account);
        return AccountStatement.generateStatement(account, bankTransactions, dateStartPeriod, dateEndPeriod);
    }

}
