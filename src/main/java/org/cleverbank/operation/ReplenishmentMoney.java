package org.cleverbank.operation;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Класс для пополнения средств на счет владельцем счета
 */
public class ReplenishmentMoney extends Check implements IOperationWithAccount {

    public void startOperation(Account senderAccount, Account receiverAccount, double money) {
        Account account = (senderAccount != null) ? senderAccount : receiverAccount;
        account.setRemainder(account.getRemainder() + money);
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.update(account);
    }

    @Override
    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        return super.generateBankTransaction(senderAccount, receiverAccount, money, type);
    }

    @Override
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = super.generateCheck(bankTransaction);

        bill += String.format("| Банк получателя: %22s |\n", bankTransaction.getAccountOfSender().getBank().getName());
        bill += String.format("| Счет получателя: %22s |\n", bankTransaction.getAccountOfSender().getNumberAccount());
        bill += String.format("| Сумма: %28.2f %3s |\n",
                bankTransaction.getSumma(),
                bankTransaction.getAccountOfSender().getCurrency().getName());
        bill += "-------------------------------------------";

        return bill;

    }

    @Override
    public void printCheck(String bill, String fileName) {
        super.printCheck(bill, fileName);
    }


}
