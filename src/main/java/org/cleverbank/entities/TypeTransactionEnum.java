package org.cleverbank.entities;

public enum TypeTransactionEnum {
    TRANSFER("перевод"),
    WITHDRAWAL("снятие средств"),
    REPLENISHMENT("пополнение счета");

    private String type;

    TypeTransactionEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TypeTransactionEnum findByType(String string) {
        String lowerCase = string.toLowerCase();
        for (TypeTransactionEnum type : values()) {
            if (type.getType().equals(lowerCase)) {
                return type;
            }
        }
        throw new EnumConstantNotPresentException(TypeTransactionEnum.class, string);
    }
}
