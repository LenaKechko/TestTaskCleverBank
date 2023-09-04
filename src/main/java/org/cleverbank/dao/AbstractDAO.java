package org.cleverbank.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Абстрактный класс. Предоставляет список страндартных методов
 * для crud-операций. Параметризированный: на первом месте тип ключа,
 * на втором тип сущности. Содержит protected экзепмляр Connection
 * для работы со Statement и PreperadStatement
 *
 * @author Кечко Елена
 */

public abstract class AbstractDAO<K extends Number, T> {
    /**
     * Поле connection
     */
    protected Connection connection;

    /**
     * Метод для просмотра всех данных из БД
     *
     * @return List объектов сущности
     */
    public abstract List<T> findAll();

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект сущности
     */
    public abstract T findEntityById(K id);

    /**
     * Метод для удаления сущности из БД по id
     *
     * @param id объекта
     * @return true/false - успешное выполнение операции или нет
     */
    public abstract boolean delete(K id);

    /**
     * Метод для удаления сущности из БД по сущности
     *
     * @param entity
     * @return true/false - успешное выполнение операции или нет
     */
    public abstract boolean delete(T entity);

    /**
     * Метод для занесения сущности в БД
     *
     * @param entity
     * @return true/false - успешное выполнение операции или нет
     */
    public abstract boolean create(T entity);

    /**
     * Метод для изменения сущности в БД
     *
     * @param entity
     * @return true/false - успешное выполнение операции или нет
     */
    public abstract boolean update(T entity);

    /**
     * Присвоение значения параметру connection
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
