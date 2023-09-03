package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AccountStatement implements IStatement {

    @Override
    public StringBuilder generateStatement(Account account,
                                           LocalDate startDate, LocalDate finishDate) {
        StringBuilder bill = IStatement.generateStatementHeader(account, startDate, finishDate);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);
        final List <BankTransaction> bankTransactions=
                CallTransaction.<List<BankTransaction>>doSelect(() ->
                        bankTransactionDAO.findEntityByDate(startDate, finishDate, account), transactionDB);

        bill.append("  Дата     |      Примечание                         |   Сумма         \n");
        bill.append("-----------------------------------------------------------------------\n");
        for (BankTransaction bankTransaction : bankTransactions) {
            String description = "";
            BigDecimal money = bankTransaction.getMoney();
            switch (bankTransaction.getType().getName()) {
                case TRANSFER -> {
                    description = "Пополнение";
                    if (bankTransaction.getAccountOfSender().getId() == account.getId()) {
                        money = money.subtract(money.multiply(BigDecimal.valueOf(2)));
                        description += " счета " +
                                bankTransaction.getAccountOfReceiver().getUser().getLastName();
                    } else {
                        description += " от " + bankTransaction.getAccountOfSender().getUser().getLastName();
                    }
                }
                case WITHDRAWAL -> {
                    description = "Снятие средств";
                    money = money.multiply(BigDecimal.valueOf(-1));
                }
                case REPLENISHMENT -> description = "Пополнение";
            }
            bill.append(String.format("%s | %-40s| %.2f %s\n",
                    formatDate.format(bankTransaction.getTransactionDate()), description, money,
                    account.getCurrency().getName()));
        }
        return bill;
    }
}
