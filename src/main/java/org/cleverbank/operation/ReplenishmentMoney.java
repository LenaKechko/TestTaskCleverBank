package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;

/**
 * Класс для пополнения средств на счет владельцем счета
 */
public class ReplenishmentMoney implements IOperationWithAccount {

    public TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount,
                                        Account receiverAccount, BigDecimal money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder().add(money));
        AccountDAO accountDAO = new AccountDAO();
        transactionDB.initTransaction(accountDAO);
        accountDAO.update(account);
        return transactionDB;
    }

    @Override
    public StringBuilder generateCheck(BankTransaction bankTransaction) {
        StringBuilder bill = IOperationWithAccount.generateCheckHeader(bankTransaction);

        bill.append(String.format("| Банк получателя: %22s |\n",
                bankTransaction.getAccountOfSender().getBank().getName()));
        bill.append(String.format("| Счет получателя: %22s |\n",
                bankTransaction.getAccountOfSender().getNumberAccount()));
        bill.append(String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getMoney(),
                bankTransaction.getAccountOfSender().getCurrency().getName()));
        bill.append("-------------------------------------------");

        return bill;
    }

}
