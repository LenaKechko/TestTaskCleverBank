package org.cleverbank.entities;

import lombok.Getter;

/**
 * Перечисление, содержащее перечень доступных транзакций
 *
 * @author Кечко Елена
 */

@Getter
public enum TypeTransactionEnum {
    TRANSFER("Перевод"),
    WITHDRAWAL("Снятие средств"),
    REPLENISHMENT("Пополнение счета");

    private final String type;

    /**
     * Конструктор с типом транзакции
     * @param type наименование транзакции
     */
    TypeTransactionEnum(String type) {
        this.type = type;
    }

    /**
     * Метод для получения константы из перечисления по наименованию типа транзакции
     * @param string наименование транзакции
     * @return тип транзакции из списка констант перечисления
     * @throws EnumConstantNotPresentException вызывается исключение,
     * если передан неизветсный тип транзакции
     * */

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
