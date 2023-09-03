package org.cleverbank.dao;

import org.cleverbank.entities.TypeCurrency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью TypeCurrency и таблицей БД type_transaction
 *
 * @author Кечко Елена
 */
public class TypeCurrencyDAO extends AbstractDAO<Integer, TypeCurrency> {
    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_CURRENCY = "SELECT * FROM type_currency";

    /**
     * Запрос на вывод данных по известному id
     */
    public static final String SQL_SELECT_CURRENCY_ID = "SELECT * FROM type_currency WHERE id = ?";

    /**
     * Метод для просмотра всех данных из таблицы type_transaction
     *
     * @return List объектов сущности
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public List<TypeCurrency> findAll() {
        List<TypeCurrency> typeCurrencies = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_CURRENCY);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                typeCurrencies.add(new TypeCurrency(id, name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeCurrencies;
    }

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект TypeCurrency
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public TypeCurrency findEntityById(Integer id) {
        TypeCurrency typeCurrency = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_CURRENCY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);

                typeCurrency = new TypeCurrency(id, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeCurrency;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(TypeCurrency entity) {
        return false;
    }

    @Override
    public boolean create(TypeCurrency entity) {
        return false;
    }

    @Override
    public boolean update(TypeCurrency entity) {
        return false;
    }
}
