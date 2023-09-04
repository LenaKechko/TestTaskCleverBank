package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;

/**
 * Класс для снятия денег со счета в банке
 *
 * @author Кечко Елена
 */
public class WithdrawalMoney implements IOperationWithAccount {

    /**
     * Метод для выполнения операции.
     * Происходит изменение остатка на счету отправителя на определенную сумму
     *
     * @param transactionDB   соединение с БД
     * @param senderAccount   счет отправителя среств (если есть или null)
     * @param receiverAccount счет получателя среств (если есть или null)
     * @param money           сумма транзакции
     * @return соединение с БД для общего закрытия транзакции
     */
    @Override
    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount,
                                        Account receiverAccount, BigDecimal money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder().subtract(money));
        AccountDAO accountDAO = new AccountDAO();
        transactionDB.initTransaction(accountDAO);
        accountDAO.update(account);
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
        bill.append(String.format("| Банк клиента: %25s |\n",
                bankTransaction.getAccountOfReceiver().getBank().getName()));
        bill.append(String.format("| Счет клиента: %25s |\n",
                bankTransaction.getAccountOfReceiver().getNumberAccount()));
        bill.append(String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfReceiver().getCurrency().getName()));
        bill.append("-------------------------------------------");

        return bill;
    }

}
