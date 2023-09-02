package org.cleverbank.menu;

import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountStatementMenu extends AbstractMenu {
    private final static String ACCOUNT_STATEMENT_MENU =
            "1. Выписка за текущий месяц\n" +
                    "2. Выписка за год\n" +
                    "3. Выписка за весь период\n" +
                    "4. Выписка за указанный период\n" +
                    "5. Вернуться к другим операциям";


    public static void start(Account account) {
        while (true) {
            printMenu(ACCOUNT_STATEMENT_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
            ArrayList<BankTransaction> bankTransactions = null;
            LocalDate dateNow = LocalDate.now(), dateStartPeriod = null, dateEndPeriod = null;
            switch (sc.nextInt()) {
                case 1:
                    dateStartPeriod = dateNow.withDayOfMonth(1);
                    dateEndPeriod = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
                    WriterMenu.start(account, dateStartPeriod, dateEndPeriod);
                    break;
                case 2:
                    dateStartPeriod = dateNow.withDayOfYear(1);
                    dateEndPeriod = dateNow.withDayOfYear(dateNow.lengthOfYear());
                    WriterMenu.start(account, dateStartPeriod, dateEndPeriod);
                    break;
                case 3:
                    dateStartPeriod =
                            Instant.ofEpochMilli(account.getOpeningDate().getTime())
                                    .atZone(ZoneId.systemDefault()).toLocalDate();
                    dateEndPeriod = dateNow;
                    WriterMenu.start(account, dateStartPeriod, dateEndPeriod);
                    break;
                case 4:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.println("Введите начальную дату (дд-мм-гггг):");
                    dateStartPeriod = LocalDate.parse(scanner.nextLine(), formatter);
                    System.out.println("Введите конечную дату (дд-мм-гггг):");
                    dateEndPeriod = LocalDate.parse(scanner.nextLine(), formatter);
                    WriterMenu.start(account, dateStartPeriod, dateEndPeriod);
                    break;
                case 5:
                    return;

            }
        }
    }
}
