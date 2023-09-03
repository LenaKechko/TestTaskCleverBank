package org.cleverbank.entities;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeTransaction {
    private int id;
    private TypeTransactionEnum name;

    @Override
    public String toString() {
        return "Тип транзакции: " + name.getType();
    }
}
