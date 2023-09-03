package org.cleverbank.operation;

import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AccountStatement {
    public static StringBuilder generateStatement(Account account, List<BankTransaction> bankTransactions,
                                           LocalDate startDate, LocalDate finishDate) {
        Date dateNow = Date.from(Instant.now());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH.mm");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        StringBuilder bill = new StringBuilder("                           Выписка\n");
        bill.append(String.format("                             %s\n", account.getBank().getName()));
        bill.append(String.format("Клиент                         | %s %s %s\n",
                account.getUser().getLastName(),
                account.getUser().getName(),
                account.getUser().getMiddleName()));
        bill.append(String.format("Счет                           | %s\n", account.getNumberAccount()));
        bill.append(String.format("Валюта                         | %s\n", account.getCurrency().getName()));
        bill.append(String.format("Дата открытия                  | %s\n",
                formatDate.format(account.getOpeningDate())));
        bill.append(String.format("Период                         | %s - %s\n",
                startDate.format(formatter),
                finishDate.format(formatter)));
        bill.append(String.format("Дата и время формирования      | %s, %s\n",
                formatDate.format(dateNow),
                formatTime.format(dateNow)));
        bill.append(String.format("Остаток                        | %.2f %s\n",
                account.getRemainder(), account.getCurrency().getName()));
        bill.append("  Дата     |      Примечание                         |   Сумма         \n");
        bill.append("-----------------------------------------------------------------------\n");
        for (BankTransaction bankTransaction : bankTransactions) {
            String description = "";
            BigDecimal money = bankTransaction.getMoney();
            switch (bankTransaction.getType().getName()) {
                case TRANSFER -> {
                    description = "Пополнение";
                    if (bankTransaction.getAccountOfSender().getId() == account.getId()) {
                        money = money.subtract(money.multiply(BigDecimal.valueOf(2)));
                        description += " счета " +
                                bankTransaction.getAccountOfReceiver().getUser().getLastName();
                    } else {
                        description += " от " + bankTransaction.getAccountOfSender().getUser().getLastName();
                    }
                }
                case WITHDRAWAL -> {
                    description = "Снятие средств";
                    money = money.multiply(BigDecimal.valueOf(-1));
                }
                case REPLENISHMENT -> {
                    description = "Пополнение";
//                    money = bankTransaction.getMoney();
                }
            }
            bill.append(String.format("%s | %-40s| %.2f %s\n",
                    formatDate.format(bankTransaction.getTransactionDate()), description, money,
                    account.getCurrency().getName()));
        }
        return bill;
    }
}
