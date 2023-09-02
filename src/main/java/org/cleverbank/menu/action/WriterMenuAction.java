package org.cleverbank.menu.action;

import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.dao.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.operation.AccountStatement;

import java.time.LocalDate;
import java.util.ArrayList;

public class WriterMenuAction {

    public static String getStatement(Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);
        try {
            ArrayList<BankTransaction> bankTransactions =
                    bankTransactionDAO.findEntityByDate(dateStartPeriod, dateEndPeriod, account);
            transactionDB.commit();
            return AccountStatement.generateStatement(account, bankTransactions, dateStartPeriod, dateEndPeriod);
        } catch (Exception e) {
            transactionDB.rollback();
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
        return "";

    }

}
