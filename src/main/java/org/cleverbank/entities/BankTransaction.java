package org.cleverbank.entities;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
/*здесь необходимо описать класс или интерфейс и разбить разного типа транзакции по классам*/
public class BankTransaction {
    private int numberCheck;
    private Date transactionDate;
    private TypeTransaction type;
    private Double money;
    private Account accountOfSender;
    private Account accountOfReceiver;
}
