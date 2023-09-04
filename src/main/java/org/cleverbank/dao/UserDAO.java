package org.cleverbank.dao;

import org.cleverbank.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью User и таблицей БД users
 *
 * @author Кечко Елена
 */
public class UserDAO extends AbstractDAO<Integer, User> {

    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";

    /**
     * Запрос на вывод всех данных по известному id
     */
    public static final String SQL_SELECT_USER_ID = "SELECT * FROM users WHERE id = ?";

    /**
     * Запрос на вывод всех данных по фио
     */
    public static final String SQL_SELECT_USER_FULL_NAME =
            "SELECT id FROM users WHERE lastname = ? and name = ? and middlename = ?";
    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_USER =
            "INSERT INTO users(lastname, name, middlename, address, phone_number) VALUES (?, ?, ?, ?, ?)";
    /**
     * Запрос на удаление данных из таблицы БД по известному id
     */
    public static final String SQL_DELETE_USER_ID = "DELETE FROM users WHERE id = ?";
    /**
     * Запрос на удаление данных из таблицы БД по фио
     */
    public static final String SQL_DELETE_USER_FULL_NAME =
            "DELETE FROM users WHERE lastname = ? and name = ? and middlename = ?";
    /**
     * Запрос на изменение данных из таблицы БД по известному id
     */
    public static final String SQL_UPDATE_USER =
            "UPDATE users SET lastname = ?, name = ?, middlename = ?, address = ?, phone_number = ? WHERE id = ?";

    /**
     * Метод для просмотра всех данных из таблицы users
     *
     * @return List объектов сущности
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                int id = rs.getInt(1);
                String lastName = rs.getString(2);
                String name = rs.getString(3);
                String middleName = rs.getString(4);
                String address = rs.getString(5);
                String phone = rs.getString(6);
                users.add(new User(id, lastName, name, middleName, address, phone));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект User
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public User findEntityById(Integer id) {
        User user = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String lastName = rs.getString(2);
                String name = rs.getString(3);
                String middleName = rs.getString(4);
                String address = rs.getString(5);
                String phoneNumber = rs.getString(6);

                user = new User(id, lastName, name, middleName, address, phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    /**
     * Метод для нахождения id из БД по фио пользователя
     *
     * @param user объект пользователь
     * @return id пользователя
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public Integer findEntityByFullName(User user) {
        int id = 0;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_FULL_NAME)) {
            statement.setString(1, user.getLastName());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMiddleName());
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для удаления сущности из БД по фио пользователя
     *
     * @param user объект пользователь
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean delete(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_FULL_NAME)) {
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMiddleName());
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
     * @param user объект типа User
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMiddleName());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getPhoneNumber());
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
     * @param user объект типа User
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMiddleName());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
