package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

import java.util.concurrent.atomic.AtomicReference;

public class UserOperationWithAccount {


    private IOperationWithAccount operation;

    public void runOperation(TypeTransaction typeTransaction, Account senderAccount, Account receiverAccount, double money) {
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        AtomicReference<TransactionDB> transactionDB = new AtomicReference<>(new TransactionDB());

        switch (typeTransaction.getName()) {
            case TRANSFER -> operation = new TransferMoney();
            case WITHDRAWAL -> operation = new WithdrawalMoney();
            case REPLENISHMENT -> operation = new ReplenishmentMoney();
        }
        transactionDB.get().initTransaction(bankTransactionDAO);
        CallTransaction.doTransaction(() -> {
            transactionDB.set(operation.startOperation(transactionDB.get(), senderAccount, receiverAccount, money));
            BankTransaction  operationCheck = operation.generateBankTransaction(
                    senderAccount, receiverAccount, money, typeTransaction);
            bankTransactionDAO.create(operationCheck);
            int numberCheck = bankTransactionDAO.findNumberCheckByBankTransaction(operationCheck);
            operationCheck.setNumberCheck(numberCheck);
            String bill = operation.generateCheck(operationCheck);
            operation.printCheck(bill, "check" + operationCheck.getNumberCheck());
        }, transactionDB.get());
    }
}
