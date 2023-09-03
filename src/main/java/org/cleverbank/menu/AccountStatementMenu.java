package org.cleverbank.menu;

import org.cleverbank.entities.Account;
import org.cleverbank.operation.IStatement;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountStatementMenu extends AbstractMenu {
    private final static String ACCOUNT_STATEMENT_MENU =
            """
                    1. Выписка за текущий месяц
                    2. Выписка за год
                    3. Выписка за весь период
                    4. Выписка за указанный период
                    5. Вернуться к другим операциям""";

    public static void start(Account account, IStatement statement) {
        while (true) {
            printMenu(ACCOUNT_STATEMENT_MENU);
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            LocalDate dateNow = LocalDate.now(), dateStartPeriod, dateEndPeriod;
            switch (sc.nextInt()) {
                case 1 -> {
                    dateStartPeriod = dateNow.withDayOfMonth(1);
                    dateEndPeriod = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
                    WriterMenu.start(statement, account, dateStartPeriod, dateEndPeriod);
                }
                case 2 -> {
                    dateStartPeriod = dateNow.withDayOfYear(1);
                    dateEndPeriod = dateNow.withDayOfYear(dateNow.lengthOfYear());
                    WriterMenu.start(statement, account, dateStartPeriod, dateEndPeriod);
                }
                case 3 -> {
                    dateStartPeriod =
                            Instant.ofEpochMilli(account.getOpeningDate().getTime())
                                    .atZone(ZoneId.systemDefault()).toLocalDate();
                    dateEndPeriod = dateNow;
                    WriterMenu.start(statement, account, dateStartPeriod, dateEndPeriod);
                }
                case 4 -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.println("Введите начальную дату (дд-мм-гггг):");
                    dateStartPeriod = LocalDate.parse(scanner.nextLine(), formatter);
                    System.out.println("Введите конечную дату (дд-мм-гггг):");
                    dateEndPeriod = LocalDate.parse(scanner.nextLine(), formatter);
                    WriterMenu.start(statement, account, dateStartPeriod, dateEndPeriod);
                }
                case 5 -> {
                    return;
                }
            }
        }
    }
}
