package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

/**
 * Класс для снятия денег во счета в банке
 */
public class WithdrawalMoney extends Check implements IOperationWithAccount {
    @Override
    public void startOperation(Account senderAccount, Account receiverAccount, double money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() - money);
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.update(account);
    }

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = super.generateCheck(bankTransaction);

        bill += String.format("| Банк клиента:  %s|\n", bankTransaction.getAccountOfReceiver().getBank());
        bill += String.format("| Счет клиента:  %s|\n", bankTransaction.getAccountOfReceiver().getNumberAccount());
        bill += String.format("| Сумма:  %f %s|\n", bankTransaction.getSumma(), bankTransaction.getType().getName());
        bill = "-------------------------------------------";

        return bill;
    }

    @Override
    public void printCheck(String bill, String fileName) {
        super.printCheck(bill, fileName);
    }
}
