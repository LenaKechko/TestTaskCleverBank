package org.cleverbank.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
    private int numberCheck;
    private Date transactionDate;
    private TypeTransaction type;
    private BigDecimal money;
    private Account accountOfSender;
    private Account accountOfReceiver;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Номер чека: " + numberCheck +
                "\nДата транзакции: " + transactionDate +
                "\nТип транзакции: " + type +
                "\nСумма: " + money);
        if (accountOfSender != null) {
            result.append("\n-------Отправитель-------\n" + accountOfSender.toString());
        }
        if (accountOfReceiver != null) {
            result.append("\n--------Получатель--------\n" + accountOfReceiver.toString());
        }
        result.append("\n-------------------------------------------------");

        return result.toString();
    }
}
