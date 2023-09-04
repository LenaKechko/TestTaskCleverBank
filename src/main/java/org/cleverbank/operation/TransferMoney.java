package org.cleverbank.operation;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;

/**
 * Класс для перевода денежных средств с одного счета на другой
 *
 * @author Кечко Елена
 */
public class TransferMoney implements IOperationWithAccount {

    /**
     * Метод для выполнения операции.
     * Происходит изменение остатка на счетах получателя
     * и отправителя на определенную сумму
     *
     * @param transactionDB   соединение с БД
     * @param senderAccount   счет отправителя среств (если есть или null)
     * @param receiverAccount счет получателя среств (если есть или null)
     * @param money           сумма транзакции
     * @return соединение с БД для общего закрытия транзакции
     */
    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount,
                                        Account receiverAccount, BigDecimal money) {
        ReplenishmentMoney operationSender = new ReplenishmentMoney();
        transactionDB = operationSender.startOperation(transactionDB, null, receiverAccount, money);
        WithdrawalMoney operationReceiver = new WithdrawalMoney();
        transactionDB = operationReceiver.startOperation(transactionDB, senderAccount, null, money);
        return transactionDB;
    }

    /**
     * Метод для формирования чека проведенной транзакции
     *
     * @param bankTransaction объект транзакции
     * @return текст для чека
     */
    @Override
    public StringBuilder generateCheck(BankTransaction bankTransaction) {
        StringBuilder bill = IOperationWithAccount.generateCheckHeader(bankTransaction);

        bill.append(String.format("| Банк отправителя: %21s |\n",
                bankTransaction.getAccountOfSender().getBank().getName()));
        bill.append(String.format("| Банк получателя: %22s |\n",
                bankTransaction.getAccountOfReceiver().getBank().getName()));
        bill.append(String.format("| Счет отправителя: %21s |\n",
                bankTransaction.getAccountOfSender().getNumberAccount()));
        bill.append(String.format("| Счет получателя: %22s |\n",
                bankTransaction.getAccountOfReceiver().getNumberAccount()));
        bill.append(String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfSender().getCurrency().getName()));
        bill.append("-------------------------------------------");

        return bill;
    }

}
