package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.time.LocalDateTime;

/**
 * Класс для пополнения средств на счет владельцем счета
 * */
public class ReplenishmentMoney extends Check implements IOperationWithAccount{

    public void startOperation(Account senderAccount, Account receiverAccount, int money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() + money);
    }

    @Override
    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, int money, int type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public void generateCheck() {

    }

}
