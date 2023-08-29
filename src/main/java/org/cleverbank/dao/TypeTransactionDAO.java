package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.TypeTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionDAO extends AbstractDAO<Integer, TypeTransaction> {

    public static final String SQL_SELECT_ALL_TYPE_TRANSACTION = "SELECT * FROM type_transaction";
    public static final String SQL_SELECT_TYPE_TRANSACTION_ID = "SELECT * FROM type_transaction WHERE id = ?";
    public static final String SQL_SELECT_TYPE_TRANSACTION_TYPE = "SELECT * FROM type_transaction WHERE type = ?";

    @Override
    public List<TypeTransaction> findAll() {
        ArrayList<TypeTransaction> typeTransactions = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_TYPE_TRANSACTION);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                typeTransactions.add(new TypeTransaction(id, name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeTransactions;
    }

    @Override
    public TypeTransaction findEntityById(Integer id) {
        TypeTransaction typeTransaction = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_TYPE_TRANSACTION_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);

                typeTransaction = new TypeTransaction(id, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeTransaction;
    }

    public TypeTransaction findEntityByType(String type) {
        TypeTransaction typeTransaction = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_TYPE_TRANSACTION_TYPE)) {
            statement.setString(1, type);
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