package org.cleverbank.dao;

import org.cleverbank.entities.TypeTransaction;
import org.cleverbank.entities.TypeTransactionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью TypeTransaction и таблицей БД type_transaction
 *
 * @author Кечко Елена
 */
public class TypeTransactionDAO extends AbstractDAO<Integer, TypeTransaction> {
    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_TYPE_TRANSACTION = "SELECT * FROM type_transaction";
    /**
     * Запрос на вывод всех данных по известному id
     */
    public static final String SQL_SELECT_TYPE_TRANSACTION_ID = "SELECT * FROM type_transaction WHERE id = ?";
    /**
     * Запрос на вывод всех данных по известному типу транзакции
     */
    public static final String SQL_SELECT_TYPE_TRANSACTION_TYPE = "SELECT * FROM type_transaction WHERE type = ?";

    /**
     * Метод для просмотра всех данных из таблицы type_transaction
     *
     * @return List объектов сущности
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public List<TypeTransaction> findAll() {
        List<TypeTransaction> typeTransactions = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_TYPE_TRANSACTION);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                typeTransactions.add(new TypeTransaction(id, TypeTransactionEnum.findByType(name)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeTransactions;
    }

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект TypeTransaction
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public TypeTransaction findEntityById(Integer id) {
        TypeTransaction typeTransaction = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_TYPE_TRANSACTION_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                typeTransaction = new TypeTransaction(id, TypeTransactionEnum.findByType(name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeTransaction;
    }

    /**
     * Метод для нахождение сущности из БД по наименованию типа транзакции
     *
     * @param type объект типа TypeTransactionEnum
     * @return объект TypeTransaction
     * @throws SQLException если при работе с БД произошла ошибка
     */

    public TypeTransaction findEntityByType(TypeTransactionEnum type) {
        TypeTransaction typeTransaction = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_TYPE_TRANSACTION_TYPE)) {
            statement.setString(1, type.getType());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                typeTransaction = new TypeTransaction(id, type);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeTransaction;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(TypeTransaction entity) {
        return false;
    }

    @Override
    public boolean create(TypeTransaction entity) {
        return false;
    }

    @Override
    public boolean update(TypeTransaction entity) {
        return false;
    }
}
