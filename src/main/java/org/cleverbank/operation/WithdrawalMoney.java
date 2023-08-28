package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.time.LocalDateTime;

/**
 * Класс для снятия денег во счета в банке
 */
public class WithdrawalMoney extends Check implements IOperationWithAccount {
    @Override
    public void startOperation(Account senderAccount, Account receiverAccount, int money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() - money);
    }

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, int money, int type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public void generateCheck() {

    }
}
