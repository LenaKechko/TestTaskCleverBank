package org.cleverbank.entities;

import lombok.*;

/**
 * Класс реализующий сущность тип валюты TypeCurrency, соответствует таблице БД type_currency
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeCurrency {
    /**
     * Поле для уникального идентификатора
     */
    private int id;
    /**
     * Поле для названия валюты
     */
    private String name;

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
    @Override
    public String toString() {
        return "Тип валюты: " + name;
    }
}
