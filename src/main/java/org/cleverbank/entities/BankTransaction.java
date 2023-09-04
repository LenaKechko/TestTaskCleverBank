package org.cleverbank.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Класс реализующий сущность совершаемыю транзакций BankTransaction, соответствует таблице БД transactions
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
    /**
     * Поле для уникального номера чека операции
     */
    private int numberCheck;
    /**
     * Поле для даты транзакции
     */
    private Date transactionDate;
    /**
     * Поле для типа транзакции
     */
    private TypeTransaction type;
    /**
     * Поле для суммы, участвующей в транзакции
     */
    private BigDecimal money;
    /**
     * Поле для связи с отправителем денежных средств, может быть равно null
     */
    private Account accountOfSender;
    /**
     * Поле для связи с получателем денежных средств, может быть равно null
     */
    private Account accountOfReceiver;

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Номер чека: " + numberCheck +
                "\nДата транзакции: " + transactionDate +
                "\nТип транзакции: " + type +
                "\nСумма: " + money);
        if (accountOfSender != null) {
            result.append("\n-------Отправитель-------\n" + accountOfSender);
        }
        if (accountOfReceiver != null) {
            result.append("\n--------Получатель--------\n" + accountOfReceiver);
        }
        result.append("\n-------------------------------------------------");

        return result.toString();
    }
}
