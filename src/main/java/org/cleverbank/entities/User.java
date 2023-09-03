package org.cleverbank.entities;

import lombok.*;

/**
 * Класс реализующий сущность пользователь User, соответствует таблице БД users
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса,
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Поле для уникального идентификатора
     */
    private int id;
    /**
     * Поле для фамилии пользователя
     */
    private String lastName;
    /**
     * Поле для имени пользователя
     */
    private String name;
    /**
     * Поле для отчества пользователя
     */
    private String middleName;
    /**
     * Поле для адреса пользователя
     */
    private String address;
    /**
     * Поле для телефона пользователя
     */
    private String phoneNumber;

    /**
     * Конструктор для создания пользователя по фио
     *
     * @param lastName   фамилия
     * @param name       имя
     * @param middleName отчество
     */
    public User(String lastName, String name, String middleName) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
    }

    /**
     * Метод для вывода данных в удобном пользователю формате
     *
     * @return строку для дальнейшего вывода
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ФИО: " + lastName + " " + name + " " + middleName +
                "\nАдрес: " + address +
                "\nНомер телефона: " + phoneNumber +
                "\n------------------------------------");
        return result.toString();
    }
}
