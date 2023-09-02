package org.cleverbank.menu;

import org.cleverbank.entities.Account;
import org.cleverbank.menu.action.WriterMenuAction;
import org.cleverbank.writer.IWriter;
import org.cleverbank.writer.Writer;
import org.cleverbank.writer.WriterPDF;
import org.cleverbank.writer.WriterTXT;

import java.time.LocalDate;
import java.util.Scanner;

public class WriterMenu extends AbstractMenu {
    private final static String WRITER_MENU =
            "1. Вывести в PDF\n" +
                    "2. Вывести в TXT\n" +
                    "3. Вернуться в предыдущее меню";

    public static void start(Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        String accountStatement = WriterMenuAction.getStatement(account, dateStartPeriod, dateEndPeriod);
        Writer writer = null;
        while (true) {
            printMenu(WRITER_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    IWriter writerPDF = new WriterPDF();
                    writer = new Writer(writerPDF);
                    writer.runWriter(accountStatement, "statement\\statement" + account.getNumberAccount());
                    break;
                case 2:
                    IWriter writerTXT = new WriterTXT();
                    writer = new Writer(writerTXT);
                    writer.runWriter(accountStatement, "statement\\statement" + account.getNumberAccount());
                    break;
                case 3:
                    return;

            }
        }
    }

}
