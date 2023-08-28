package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.time.LocalDateTime;

public class TransferMoney extends Check implements IOperationWithAccount{
    public void startOperation(Account senderAccount, Account receiverAccount, int money) {
        //ReplenishmentMoney.startOperation(senderAccount, null, money);
        //WithdrawalMoney.startOperation(null, receiverAccount, money);
    }

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, int money, int type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public void generateCheck() {

    }
}
