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
            """
                    1. Вывести в PDF
                    2. Вывести в TXT
                    3. Вернуться в предыдущее меню""";
    private static final String STATEMENTS_STATEMENT = "statements\\statement";

    public static void start(Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        StringBuilder accountStatement = WriterMenuAction.getStatement(account, dateStartPeriod, dateEndPeriod);
        Writer writer;
        while (true) {
            printMenu(WRITER_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> {
                    IWriter writerPDF = new WriterPDF();
                    writer = new Writer(writerPDF);
                    writer.runWriter(accountStatement, STATEMENTS_STATEMENT + account.getNumberAccount());
                }
                case 2 -> {
                    IWriter writerTXT = new WriterTXT();
                    writer = new Writer(writerTXT);
                    writer.runWriter(accountStatement, STATEMENTS_STATEMENT + account.getNumberAccount());
                }
                case 3 -> {
                    return;
                }
            }
        }
    }

}
