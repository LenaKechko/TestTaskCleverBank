package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.time.LocalDateTime;

public class Check {

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, int money, int type) {
        BankTransaction check = new BankTransaction();
        check.setTransactionDate(LocalDateTime.now());
        check.setSumma(money);
        check.setType(type); //Брать с базы?
        check.setAccountOfSender(senderAccount);
        check.setAccountOfReceiver(receiverAccount);
        return check;
    }
}
