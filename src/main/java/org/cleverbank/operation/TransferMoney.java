package org.cleverbank.operation;

import org.cleverbank.dao.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

public class TransferMoney extends Check implements IOperationWithAccount {
    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, double money) {
        ReplenishmentMoney operationSender = new ReplenishmentMoney();
        transactionDB = operationSender.startOperation(transactionDB, null, receiverAccount, money);
        WithdrawalMoney operationReceiver = new WithdrawalMoney();
        transactionDB = operationReceiver.startOperation(transactionDB, senderAccount, null, money);
        return transactionDB;
    }

    @Override
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = super.generateCheck(bankTransaction);

        bill += String.format("| Банк отправителя: %21s |\n", bankTransaction.getAccountOfSender().getBank().getName());
        bill += String.format("| Банк получателя: %22s |\n", bankTransaction.getAccountOfReceiver().getBank().getName());
        bill += String.format("| Счет отправителя: %21s |\n", bankTransaction.getAccountOfSender().getNumberAccount());
        bill += String.format("| Счет получателя: %22s |\n", bankTransaction.getAccountOfReceiver().getNumberAccount());
        bill += String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfSender().getCurrency().getName());
        bill += "-------------------------------------------";

        return bill;
    }

}
