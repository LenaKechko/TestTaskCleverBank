package org.cleverbank.operation;

import org.cleverbank.entities.Account;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Интерфейс для формирования выписок различных видов
 *
 * @author Кечко Елена
 */

public interface IStatement {
    /**
     * Поле для формата даты
     */
    SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
    /**
     * Поле для формата времени
     */
    SimpleDateFormat formatTime = new SimpleDateFormat("HH.mm");
    /**
     * Поле для формата даты
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Метод для формаирования заголовка выписки
     * на основе данных об аккаунте и временного промежутка
     *
     * @param account    объект счета
     * @param startDate  начало временного промежутка
     * @param finishDate окончание временного промежутка
     * @return текст шапки выписки
     */
    static StringBuilder generateStatementHeader(Account account,
                                                 LocalDate startDate, LocalDate finishDate) {
        Date dateNow = Date.from(Instant.now());
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
        return bill;
    }

    /**
     * Метод для формирования выписки конкретного вида
     *
     * @param account    объект счета
     * @param startDate  начало временного промежутка
     * @param finishDate окончание временного промежутка
     * @return текст шапки выписки
     */
    StringBuilder generateStatement(Account account, LocalDate startDate, LocalDate finishDate);
}
