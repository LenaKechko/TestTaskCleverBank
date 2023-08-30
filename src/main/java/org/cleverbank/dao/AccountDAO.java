package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.Bank;
import org.cleverbank.entities.TypeCurrency;
import org.cleverbank.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO extends AbstractDAO<Integer, Account> {

    public static final String SQL_SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";

    public static final String SQL_SELECT_ACCOUNT_ID = "SELECT * FROM accounts WHERE id = ?";
    public static final String SQL_SELECT_ACCOUNT_NUMBER_ACCOUNT = "SELECT * FROM accounts WHERE number_account = ?";

    public static final String SQL_SELECT_ACCOUNT_USER = "SELECT * FROM accounts " +
            "WHERE id_user = (SELECT id FROM users WHERE lastname = ? and name = ? and middlename = ?)";

    public static final String SQL_SELECT_ACCOUNT_BANK = "SELECT * FROM accounts " +
            "WHERE id_bank = (SELECT id FROM banks WHERE name = ?)";
    public static final String SQL_INSERT_ACCOUNT =
            "INSERT INTO accounts(number_account, opening_date, remainder, id_user, id_bank, id_currency) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SQL_DELETE_ACCOUNT_ID = "DELETE FROM accounts WHERE id = ?";
    public static final String SQL_DELETE_ACCOUNT_NUMBER = "DELETE FROM accounts WHERE number_account = ?";
    public static final String SQL_DELETE_ACCOUNT_USER = "DELETE FROM accounts WHERE id_user = ?";

    public static final String SQL_DELETE_ACCOUNT_BANK = "DELETE FROM accounts WHERE id_bank = ?";

    public static final String SQL_UPDATE_ACCOUNT =
            "UPDATE accounts SET opening_date = ?, remainder = ?, id_user = ?, id_bank = ?, id_currency = ? " +
                    "WHERE number_account = ?";

    public static final String SQL_UPDATE_ACCOUNT_REMAINDER =
            "UPDATE accounts SET remainder = ? " +
                    "WHERE number_account = ?";

    private UserDAO userDAO = new UserDAO();
    private BankDAO bankDAO = new BankDAO();
    private TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ACCOUNTS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                Double remainder = rs.getDouble("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                accounts.add(new Account(id, numberAccount, openingDate, remainder, user, bank, currency));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    @Override
    public Account findEntityById(Integer id) {
        Account account = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                Double remainder = rs.getDouble("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    public Account findEntityByNumberAccount(String numberAccount) {
        Account account = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_NUMBER_ACCOUNT)) {
            statement.setString(1, numberAccount);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Date openingDate = rs.getDate("opening_date");
                Double remainder = rs.getDouble("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    public Account findEntityByUser(User user) {
        Account account = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_USER)) {
            statement.setString(1, user.getLastName());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMiddleName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                Double remainder = rs.getDouble("remainder");
                user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    public Account findEntityByBank(Bank bank) {
        Account account = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_BANK)) {
            statement.setString(1, bank.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                Double remainder = rs.getDouble("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Account entity) {
        return false;
    }

    public boolean delete(User user) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(Bank bank) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BANK)) {
            preparedStatement.setInt(1, bank.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean create(Account account) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT)) {
            preparedStatement.setString(1, account.getNumberAccount());
            preparedStatement.setTimestamp(2, new Timestamp(account.getOpeningDate().getTime()));
            preparedStatement.setDouble(3, account.getRemainder());
            preparedStatement.setInt(4, account.getUser().getId());
            preparedStatement.setInt(5, account.getBank().getId());
            preparedStatement.setInt(6, account.getCurrency().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Account account) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT)) {
            preparedStatement.setTimestamp(1, new Timestamp(account.getOpeningDate().getTime()));
            preparedStatement.setDouble(2, account.getRemainder());
            preparedStatement.setInt(3, account.getUser().getId());
            preparedStatement.setInt(4, account.getBank().getId());
            preparedStatement.setInt(5, account.getCurrency().getId());
            preparedStatement.setString(6, account.getNumberAccount());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateRemainder(Account account) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_REMAINDER)) {
            preparedStatement.setDouble(1, account.getRemainder());
            preparedStatement.setString(2, account.getNumberAccount());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
