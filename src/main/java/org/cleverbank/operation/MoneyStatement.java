package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MoneyStatement implements IStatement {
    private BigDecimal leavingMoney;
    private BigDecimal comingMoney;

    @Override
    public StringBuilder generateStatement(Account account, LocalDate startDate, LocalDate finishDate) {
        StringBuilder bill = IStatement.generateStatementHeader(account, startDate, finishDate);
        bill.append("            Приход           |          Расход         \n");
        bill.append("-------------------------------------------------------\n");

        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);
        
        CallTransaction.doSelect(() -> {
            comingMoney = bankTransactionDAO.findTotalSumComingMoney(account, startDate, finishDate);
            leavingMoney = bankTransactionDAO.findTotalSumLeavingMoney(account, startDate, finishDate);
        }, transactionDB);

        bill.append(String.format(" %20.2f %s    |    %17.2f %s\n",
                comingMoney, account.getCurrency().getName(),
                leavingMoney, account.getCurrency().getName()));
        return bill;
    }
}
