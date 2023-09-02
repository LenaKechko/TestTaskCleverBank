package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.dao.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

/**
 * Класс для снятия денег во счета в банке
 */
public class WithdrawalMoney extends Check implements IOperationWithAccount {
    @Override
    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, double money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() - money);
        try {
            AccountDAO accountDAO = new AccountDAO();
            transactionDB.initTransaction(accountDAO);
            accountDAO.update(account);
        } catch (Exception e) {
            transactionDB.rollback();
            e.printStackTrace();
        }
        return transactionDB;
    }

    @Override
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = super.generateCheck(bankTransaction);
        bill += String.format("| Банк клиента: %25s |\n", bankTransaction.getAccountOfReceiver().getBank().getName());
        bill += String.format("| Счет клиента: %25s |\n", bankTransaction.getAccountOfReceiver().getNumberAccount());
        bill += String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfReceiver().getCurrency().getName());
        bill += "-------------------------------------------";

        return bill;
    }

}
