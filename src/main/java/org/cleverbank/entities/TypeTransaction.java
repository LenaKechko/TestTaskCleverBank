package org.cleverbank.entities;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypeTransaction {
    private int id;
    private TypeTransactionEnum name;
}
