package org.cleverbank.entities;

import lombok.*;

import java.time.LocalDateTime;
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
    private Double summa;
    private Account accountOfSender;
    private Account accountOfReceiver;
}