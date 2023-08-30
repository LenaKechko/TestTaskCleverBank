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
//43
        bill += String.format("| Банк клиента: %25s |\n", bankTransaction.getAccountOfReceiver().getBank().getName());
        bill += String.format("| Счет клиента: %25s |\n", bankTransaction.getAccountOfReceiver().getNumberAccount());
        bill += String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getSumma(),
                bankTransaction.getAccountOfReceiver().getCurrency().getName());
        bill += "-------------------------------------------";

        return bill;
    }

    @Override
    public void printCheck(String bill, String fileName) {
        super.printCheck(bill, fileName);
    }
}
