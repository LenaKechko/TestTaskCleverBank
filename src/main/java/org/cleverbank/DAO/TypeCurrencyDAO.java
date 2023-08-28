package org.cleverbank.DAO;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.Bank;
import org.cleverbank.entities.TypeCurrency;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeCurrencyDAO extends AbstractDAO<Integer, TypeCurrency> {

    public static final String SQL_SELECT_ALL_CURRENCY = "SELECT * FROM type_currency";
    public static final String SQL_SELECT_CURRENCY_ID = "SELECT * FROM type_currency WHERE id = ?";
    @Override
    public List<TypeCurrency> findAll() {
        ArrayList<TypeCurrency> typeCurrencies = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
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

    @Override
    public TypeCurrency findEntityById(Integer id) {
        TypeCurrency typeCurrency = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
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
