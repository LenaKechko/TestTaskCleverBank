package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

public class UserOperationWithAccount {
    private IOperationWithAccount operation;

    public void runOperation(String nameOperation, Account senderAccount, Account receiverAccount, int money) {
        BankTransaction operationCheck = null;
        switch (nameOperation) {
            case "перевод":
                operation = new TransferMoney();
                operation.startOperation(senderAccount, receiverAccount, money);
                operationCheck = operation.generateBankTransaction(senderAccount, receiverAccount, money, 1);
                break;
            case "снятие средств":
                operation = new WithdrawalMoney();
                operation.startOperation(senderAccount, receiverAccount, money);
                operationCheck = operation.generateBankTransaction(senderAccount, receiverAccount, money, 1);

                break;
            case "пополнение счета":
                operation = new ReplenishmentMoney();
                operation.startOperation(senderAccount, receiverAccount, money);
                operationCheck = operation.generateBankTransaction(senderAccount, receiverAccount, money, 1);
                break;
        }
    }
}
