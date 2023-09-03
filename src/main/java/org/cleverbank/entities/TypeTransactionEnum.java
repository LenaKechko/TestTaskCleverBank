package org.cleverbank.entities;

import lombok.Getter;

@Getter
public enum TypeTransactionEnum {
    TRANSFER("Перевод"),
    WITHDRAWAL("Снятие средств"),
    REPLENISHMENT("Пополнение счета");

    private final String type;

    TypeTransactionEnum(String type) {
        this.type = type;
    }


    public static TypeTransactionEnum findByType(String string) {
        String lowerCase = string.toLowerCase();
        for (TypeTransactionEnum type : values()) {
            if (type.getType().toLowerCase().equals(lowerCase)) {
                return type;
            }
        }
        throw new EnumConstantNotPresentException(TypeTransactionEnum.class, string);
    }
}
