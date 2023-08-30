package org.cleverbank.operation;

import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

public class UserOperationWithAccount {
    private IOperationWithAccount operation;

    public void runOperation(TypeTransaction typeTransaction, Account senderAccount, Account receiverAccount, double money) {
        BankTransaction operationCheck = null;
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        switch (typeTransaction.getName().toLowerCase()) {
            case "перевод":
                operation = new TransferMoney();
                break;
            case "снятие средств":
                operation = new WithdrawalMoney();
                break;
            case "пополнение счета":
                operation = new ReplenishmentMoney();
                break;
        }
        operation.startOperation(senderAccount, receiverAccount, money);
        operationCheck = operation.generateBankTransaction(senderAccount, receiverAccount, money, typeTransaction);
        bankTransactionDAO.create(operationCheck);
        int numberCheck = bankTransactionDAO.findNumberCheckByBankTransaction(operationCheck);
        operationCheck.setNumberCheck(numberCheck);
        String bill = operation.generateCheck(operationCheck);
        operation.printCheck(bill, "check" + operationCheck.getNumberCheck() + ".txt");
    }
}
