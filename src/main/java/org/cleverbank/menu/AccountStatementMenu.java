package org.cleverbank.menu;

import org.cleverbank.dao.AccountDAO;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.dao.TypeTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.operation.AccountStatementTXT;
import org.cleverbank.operation.UserOperationWithAccount;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
            LocalDate dateNow = LocalDate.now();
            switch (sc.nextInt()) {
                case 1:
                    LocalDate dateStartOfMonth = dateNow.withDayOfMonth(1);
                    LocalDate dateEndOfMonth = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
                    bankTransactions =
                            bankTransactionDAO.findEntityByDate(dateStartOfMonth, dateEndOfMonth, account);
                    AccountStatementTXT.generateStatement(account, bankTransactions, dateStartOfMonth, dateEndOfMonth);
                    break;
                case 2:
                    LocalDate dateStartOfYear = dateNow.withDayOfYear(1);
                    LocalDate dateEndOfYear = dateNow.withDayOfYear(dateNow.lengthOfYear());
                    bankTransactions =
                            bankTransactionDAO.findEntityByDate(dateStartOfYear, dateEndOfYear, account);
                    AccountStatementTXT.generateStatement(account, bankTransactions, dateStartOfYear, dateEndOfYear);
                    break;
                case 3:
                    LocalDate dateStart = account.getOpeningDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate dateEnd = dateNow;
                    bankTransactions =
                            bankTransactionDAO.findEntityByDate(dateStart, dateEnd, account);
                    AccountStatementTXT.generateStatement(account, bankTransactions, dateStart, dateEnd);
                    break;
                case 4:
                    System.out.println("Введите начальную дату:");
                    LocalDate dateStartPeriod = LocalDate.parse(scanner.nextLine());
                    System.out.println("Введите конечную дату:");
                    LocalDate dateEndPeriod = LocalDate.parse(scanner.nextLine());
                    bankTransactions =
                            bankTransactionDAO.findEntityByDate(dateStartPeriod, dateEndPeriod, account);
                    AccountStatementTXT.generateStatement(account, bankTransactions, dateStartPeriod, dateEndPeriod);
                    break;
                case 5:
                    return;
            }
        }
    }
}
