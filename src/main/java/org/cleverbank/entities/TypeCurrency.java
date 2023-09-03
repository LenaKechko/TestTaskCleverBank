package org.cleverbank.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeCurrency {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Тип валюты: " + name;
    }
}
