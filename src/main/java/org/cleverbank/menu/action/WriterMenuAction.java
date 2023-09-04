package org.cleverbank.menu.action;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.operation.AccountStatement;

import java.time.LocalDate;
import java.util.List;

public class WriterMenuAction {

    public static StringBuilder getStatement(Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);
        final List <BankTransaction> bankTransactions=
        CallTransaction.<List<BankTransaction>>doSelect(() ->
                bankTransactionDAO.findEntityByDate(dateStartPeriod, dateEndPeriod, account), transactionDB);

        return AccountStatement.generateStatement(account, bankTransactions, dateStartPeriod, dateEndPeriod);
    }

}
