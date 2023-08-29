package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankDAO extends AbstractDAO<Integer, Bank> {

    public static final String SQL_SELECT_ALL_BANKS = "SELECT * FROM banks";
    public static final String SQL_SELECT_BANK_ID = "SELECT * FROM banks WHERE id = ?";

    public static final String SQL_SELECT_BANK_NAME = "SELECT id FROM banks WHERE name = ?";
    public static final String SQL_INSERT_BANK =
            "INSERT INTO banks(name, address) VALUES (?, ?)";

    public static final String SQL_DELETE_BANK_ID = "DELETE FROM banks WHERE id = ?";
    public static final String SQL_DELETE_BANK_NAME = "DELETE FROM banks WHERE name = ?";

    public static final String SQL_UPDATE_BANK =
            "UPDATE banks SET name = ?, address = ? WHERE id = ?";

    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
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

    @Override
    public Bank findEntityById(Integer id) {
        Bank bank = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
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

    public Integer findEntityByName(String name) {
        Integer id = 0;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
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

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Bank bank) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_NAME)) {
            preparedStatement.setString(1, bank.getName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean create(Bank bank) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BANK)) {
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getAddress());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Bank bank) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK)) {
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
