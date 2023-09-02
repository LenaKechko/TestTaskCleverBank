package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

/**
 * Класс для пополнения средств на счет владельцем счета
 */
public class ReplenishmentMoney implements IOperationWithAccount {

    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, double money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() + money);
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
        String bill = IOperationWithAccount.generateCheckHeader(bankTransaction);

        bill += String.format("| Банк получателя: %22s |\n", bankTransaction.getAccountOfSender().getBank().getName());
        bill += String.format("| Счет получателя: %22s |\n", bankTransaction.getAccountOfSender().getNumberAccount());
        bill += String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfSender().getCurrency().getName());
        bill += "-------------------------------------------";

        return bill;
    }

}
