package org.cleverbank.menu;

import org.cleverbank.entities.Account;
import org.cleverbank.operation.IStatement;
import org.cleverbank.operation.MoneyStatement;
import org.cleverbank.writer.IWriter;
import org.cleverbank.writer.Writer;
import org.cleverbank.writer.WriterPDF;
import org.cleverbank.writer.WriterTXT;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Класс, отвечающий за меню работы с выписками по счету:
 * выбор формата документы для формирования выписки
 *
 * @author Кечко Елена
 */

public class WriterMenu extends AbstractMenu {

    /**
     * Константа вида меню выбора формата документа
     */
    private final static String WRITER_MENU =
            """
                    1. Вывести в PDF
                    2. Вывести в TXT
                    3. Вернуться в предыдущее меню""";

    /**
     * Константа, определяющая часть имени файла
     * для развернутой выписки со счета
     */
    private static final String STATEMENTS_STATEMENT = "statements\\statement";
    /**
     * Константа, определяющая часть имени файла
     * для выписки, содержащий потраченные
     * и полученные денежные средства
     */
    private static final String STATEMENT_MONEY = "statement-money\\statement-money";

    /**
     * Метод позволяющий выбрать формат документа для формирования выписок
     *
     * @param account объект пользователя
     * @param statement вид выписка определенный интерфейсом IStatement
     * @param dateStartPeriod начало временного промежутка для выбора транзакций
     * @param dateEndPeriod конец временного промежутка для выбора транзакций
     */
    public static void start(IStatement statement, Account account, LocalDate dateStartPeriod, LocalDate dateEndPeriod) {
        StringBuilder accountStatement = statement.generateStatement(account, dateStartPeriod, dateEndPeriod);
        Writer writer;
        String fileName = statement instanceof MoneyStatement ? STATEMENT_MONEY : STATEMENTS_STATEMENT;
        while (true) {
            printMenu(WRITER_MENU);
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> {
                    IWriter writerPDF = new WriterPDF();
                    writer = new Writer(writerPDF);
                    writer.runWriter(accountStatement, fileName + account.getNumberAccount());
                }
                case 2 -> {
                    IWriter writerTXT = new WriterTXT();
                    writer = new Writer(writerTXT);
                    writer.runWriter(accountStatement, fileName + account.getNumberAccount());
                }
                case 3 -> {
                    return;
                }
            }
        }
    }

}
