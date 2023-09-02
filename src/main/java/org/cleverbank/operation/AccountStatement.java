package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AccountStatement {
    public static String generateStatement(Account account, List<BankTransaction> bankTransactions,
                                           LocalDate startDate, LocalDate finishDate) {
        Date dateNow = Date.from(Instant.now());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH.mm");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String bill = "                           Выписка\n";
        bill += String.format("                             %s\n", account.getBank().getName());
        bill += String.format("Клиент                         | %s %s %s\n",
                account.getUser().getLastName(), account.getUser().getName(), account.getUser().getMiddleName());
        bill += String.format("Счет                           | %s\n", account.getNumberAccount());
        bill += String.format("Валюта                         | %s\n", account.getCurrency().getName());
        bill += String.format("Дата открытия                  | %s\n", formatDate.format(account.getOpeningDate()));
        bill += String.format("Период                         | %s - %s\n",
                startDate.format(formatter),
                finishDate.format(formatter)
        );
        bill += String.format("Дата и время формирования      | %s, %s\n",
                formatDate.format(dateNow),
                formatTime.format(dateNow));
        bill += String.format("Остаток                        | %.2f %s\n",
                account.getRemainder(), account.getCurrency().getName());
        bill += "  Дата     |      Примечание                         |   Сумма         \n";
        bill += "-----------------------------------------------------------------------\n";
        for (BankTransaction bankTransaction : bankTransactions) {
            String description = "";
            Double money = 0.0;
            switch (bankTransaction.getType().getName().toLowerCase()) {
                case "перевод":
                    description = "Пополнение";
                    money = bankTransaction.getMoney();
                    if (bankTransaction.getAccountOfSender().getId() == account.getId()) {
                        money -= 2 * money;
                        description += " счета " +
                                bankTransaction.getAccountOfReceiver().getUser().getLastName();
                    } else {
                        description += " от " + bankTransaction.getAccountOfSender().getUser().getLastName();
                    }
                    break;
                case "снятие средств":
                    description = "Снятие средств";
                    money = -bankTransaction.getMoney();
                    break;
                case "пополнение счета":
                    description = "Пополнение";
                    money = bankTransaction.getMoney();
                    break;
            }
            bill += String.format("%s | %-40s| %.2f %s\n",
                    formatDate.format(bankTransaction.getTransactionDate()), description, money,
                    account.getCurrency().getName());
        }
        return bill;
    }
}
