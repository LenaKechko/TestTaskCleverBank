package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

public interface IOperationWithAccount {
    public void startOperation(Account senderAccount, Account receiverAccount, double money);

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type);

    public String generateCheck(BankTransaction bankTransaction);

    public void printCheck(String bill, String fileName);
}
