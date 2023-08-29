package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

public class TransferMoney extends Check implements IOperationWithAccount {
    public void startOperation(Account senderAccount, Account receiverAccount, double money) {
        ReplenishmentMoney operationSender = new ReplenishmentMoney();
        operationSender.startOperation(senderAccount, null, money);
        WithdrawalMoney operationReceiver = new WithdrawalMoney();
        operationReceiver.startOperation(null, receiverAccount, money);
    }

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = super.generateCheck(bankTransaction);

        bill += String.format("| Банк отправителя:  %s|\n", bankTransaction.getAccountOfSender().getBank());
        bill += String.format("| Банк получателя:  %s|\n", bankTransaction.getAccountOfReceiver().getBank());
        bill += String.format("| Счет отправителя:  %s|\n", bankTransaction.getAccountOfSender().getNumberAccount());
        bill += String.format("| Счет получателя:  %s|\n", bankTransaction.getAccountOfReceiver().getNumberAccount());
        bill += String.format("| Сумма:  %f %s|\n", bankTransaction.getSumma(), bankTransaction.getType().getName());
        bill = "-------------------------------------------";

        return bill;
    }

    @Override
    public void printCheck(String bill, String fileName) {
        super.printCheck(bill, fileName);
    }
}
