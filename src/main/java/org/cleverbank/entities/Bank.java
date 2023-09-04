package org.cleverbank.entities;

import lombok.*;

/**
 * Класс реализующий сущность банк Bank, соответствует таблице БД banks
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    /**
     * Поле для уникального идентификатора
     */
    private int id;
    /**
     * Поле для наименования банка
     */
    private String name;
    /**
     * Поле для адреса данка
     */
    private String address;

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
    @Override
    public String toString() {
        return "Наименование банка: " + name +
                "\nАдрес банка: " + address +
                "\n--------------------------------";
    }
}
