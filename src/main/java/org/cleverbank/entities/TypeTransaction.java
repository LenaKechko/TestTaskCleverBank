package org.cleverbank.entities;

import lombok.*;

/**
 * Класс реализующий сущность тип транзакции TypeTransaction, соответствует таблице БД type_transaction
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeTransaction {
    /**
     * Поле для уникального идентификатора
     */
    private int id;
    /**
     * Поле для имени транзакции определенное перечислением
     * (т.к. список выполняемых транзакций определен по умолчанию)
     */
    private TypeTransactionEnum name;

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
    @Override
    public String toString() {
        return "Тип транзакции: " + name.getType();
    }
}
