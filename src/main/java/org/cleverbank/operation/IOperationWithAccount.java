package org.cleverbank.operation;

import org.cleverbank.dao.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

public interface IOperationWithAccount {
    TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, double money);

    BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type);

    String generateCheck(BankTransaction bankTransaction);

    void printCheck(String bill, String fileName);
}
