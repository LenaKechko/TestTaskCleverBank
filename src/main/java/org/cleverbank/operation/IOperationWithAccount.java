package org.cleverbank.operation;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;
import org.cleverbank.writer.IWriter;
import org.cleverbank.writer.Writer;
import org.cleverbank.writer.WriterTXT;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

public interface IOperationWithAccount {
    TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, double money);

    default BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        BankTransaction check = new BankTransaction();
        check.setTransactionDate(Date.from(Instant.now()));
        check.setMoney(money);
        check.setType(type);
        check.setAccountOfSender(senderAccount);
        check.setAccountOfReceiver(receiverAccount);
        return check;
    }

    static String generateCheckHeader(BankTransaction bankTransaction) {
        String bill = "-------------------------------------------\n";
        bill += "|            Банковский чек               |\n";
        bill += String.format("| Чек: %34d |\n", bankTransaction.getNumberCheck());
        SimpleDateFormat formatterForDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm:ss");
        bill += String.format("| %10s                     %8s |\n",
                formatterForDate.format(bankTransaction.getTransactionDate()),
                formatterForTime.format(bankTransaction.getTransactionDate()));
        bill += String.format("| Тип транзакции: %23s |\n", bankTransaction.getType().getName());
        return bill;
    }

    String generateCheck(BankTransaction bankTransaction);

    default void printCheck(String bill, String fileName) {
        IWriter writerTXT = new WriterTXT();
        Writer writer = new Writer(writerTXT);
        writer.runWriter(bill, "check\\" + fileName);
    }
}
