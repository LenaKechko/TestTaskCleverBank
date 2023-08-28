package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

public interface IOperationWithAccount {
    public void startOperation(Account senderAccount, Account receiverAccount, int money);

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, int money, int type);

    public void generateCheck();
}
