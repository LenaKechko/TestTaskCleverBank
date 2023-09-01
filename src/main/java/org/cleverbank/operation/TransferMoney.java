package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

public class TransferMoney extends Check implements IOperationWithAccount {
    public void startOperation(Account senderAccount, Account receiverAccount, double money) {
        ReplenishmentMoney operationSender = new ReplenishmentMoney();
        operationSender.startOperation(null, receiverAccount, money);
        WithdrawalMoney operationReceiver = new WithdrawalMoney();
        operationReceiver.startOperation(senderAccount, null, money);
    }

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
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

    @Override
    public void printCheck(String bill, String fileName) {
        super.printCheck(bill, fileName);
    }
}
