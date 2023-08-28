package org.cleverbank.DAO;

import org.cleverbank.entities.User;
import org.cleverbank.ConnectorDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<Integer, User> {

    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    public static final String SQL_SELECT_USER_ID = "SELECT * FROM users WHERE id = ?";

    public static final String SQL_SELECT_USER_FULLNAME = "SELECT id FROM users WHERE lastname = ? and name = ? and middlename = ?";
    public static final String SQL_INSERT_USER =
            "INSERT INTO users(lastname, name, middlename, address, phone_number) VALUES (?, ?, ?, ?, ?)";

    public static final String SQL_DELETE_USER_ID = "DELETE FROM users WHERE id = ?";
    public static final String SQL_DELETE_USER_FULLNAME = "DELETE FROM users WHERE lastname = ? and name = ? and middlename = ?";

    public static final String SQL_UPDATE_USER =
            "UPDATE users SET lastname = ?, name = ?, middlename = ?, address = ?, phone_number = ? WHERE id = ?";

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
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

    @Override
    public User findEntityById(Integer id) {
        User user = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
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

    public Integer findEntityByFullName(String lastName, String name, String middleName) {
        Integer id = 0;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_FULLNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, name);
            statement.setString(3, middleName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_ID)) {
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_FULLNAME)) {
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

    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
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

    @Override
    public boolean update(User user) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
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