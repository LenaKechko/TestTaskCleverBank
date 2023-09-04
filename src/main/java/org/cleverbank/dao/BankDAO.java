package org.cleverbank.dao;

import org.cleverbank.entities.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью Bank и таблицей БД banks
 *
 * @author Кечко Елена
 */
public class BankDAO extends AbstractDAO<Integer, Bank> {

    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_BANKS = "SELECT * FROM banks";
    /**
     * Запрос на вывод данных по известному id
     */
    public static final String SQL_SELECT_BANK_ID = "SELECT * FROM banks WHERE id = ?";

    /**
     * Запрос на нахождение id по известному наименованию банка
     */
    public static final String SQL_SELECT_BANK_NAME = "SELECT id FROM banks WHERE name = ?";
    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_BANK =
            "INSERT INTO banks(name, address) VALUES (?, ?)";

    /**
     * Запрос на удаление данных из таблицы БД по известному id
     */
    public static final String SQL_DELETE_BANK_ID = "DELETE FROM banks WHERE id = ?";
    /**
     * Запрос на удаление данных из таблицы БД по известному наименованию банка
     */
    public static final String SQL_DELETE_BANK_NAME = "DELETE FROM banks WHERE name = ?";
    /**
     * Запрос на изменение данных из таблицы БД по известному id
     */
    public static final String SQL_UPDATE_BANK =
            "UPDATE banks SET name = ?, address = ? WHERE id = ?";

    /**
     * Метод для просмотра всех данных из таблицы banks
     *
     * @return List объектов сущности
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_BANKS);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                banks.add(new Bank(id, name, address));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return banks;
    }

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект Bank
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public Bank findEntityById(Integer id) {
        Bank bank = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                String address = rs.getString(3);

                bank = new Bank(id, name, address);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bank;
    }
    /**
     * Метод для id из БД по наименованию банка
     *
     * @param name наименование банка
     * @return id банка
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public Integer findEntityByName(String name) {
        int id = 0;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_NAME)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    /**
     * Метод для удаления сущности из БД по id
     *
     * @param id объекта
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для удаления сущности из БД по наименованию банка
     *
     * @param bank объект банк
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean delete(Bank bank) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_NAME)) {
            preparedStatement.setString(1, bank.getName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для занесения сущности в БД
     *
     * @param bank объект типа Bank
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean create(Bank bank) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BANK)) {
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getAddress());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для изменения сущности в БД
     *
     * @param bank объект типа Bank
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean update(Bank bank) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK)) {
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getAddress());
            preparedStatement.setInt(3, bank.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
