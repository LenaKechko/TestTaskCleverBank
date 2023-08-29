package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Check {

    public BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, double money, TypeTransaction type) {
        BankTransaction check = new BankTransaction();
        check.setTransactionDate(Date.from(Instant.now()));
        check.setSumma(money);
        check.setType(type);
        check.setAccountOfSender(senderAccount);
        check.setAccountOfReceiver(receiverAccount);
        return check;
    }
    public String generateCheck(BankTransaction bankTransaction) {
        String bill = "-------------------------------------------\n";
        bill += "|            Банковский чек               |\n";
        bill += String.format("| Чек: %35d |\n", bankTransaction.getNumberCheck());
        SimpleDateFormat formatterForDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm:ss");
        bill += String.format("| %10s                     %8s |\n",
                formatterForDate.format(bankTransaction.getTransactionDate()),
                formatterForTime.format(bankTransaction.getTransactionDate()));
        bill += String.format("| Тип транзакции: %23s |\n", bankTransaction.getType().getName());
        return bill;
    }

    public void printCheck(String bill, String fileName) {
        try (FileWriter output = new FileWriter("check/" + fileName, false)) {
            output.write(bill);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
