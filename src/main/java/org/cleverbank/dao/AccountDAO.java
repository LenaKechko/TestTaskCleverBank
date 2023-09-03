package org.cleverbank.dao;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.*;

import java.math.BigDecimal;
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
    TransactionDB transactionDB = new TransactionDB();

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ACCOUNTS);
            transactionDB.initTransaction(userDAO, bankDAO, typeCurrencyDAO);
            while (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                BigDecimal remainder = rs.getBigDecimal("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                accounts.add(new Account(id, numberAccount, openingDate, remainder, user, bank, currency));
                transactionDB.commit();
            }
        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return accounts;
    }

    @Override
    public Account findEntityById(Integer id) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            transactionDB.initTransaction(userDAO, bankDAO, typeCurrencyDAO);
            if (rs.next()) {
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                BigDecimal remainder = rs.getBigDecimal("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
                transactionDB.commit();
            }
        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return account;
    }

    public Account findEntityByNumberAccount(String numberAccount) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_NUMBER_ACCOUNT)) {
            transactionDB.initTransaction(userDAO, bankDAO, typeCurrencyDAO);
            statement.setString(1, numberAccount);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Date openingDate = rs.getDate("opening_date");
                BigDecimal remainder = rs.getBigDecimal("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
                transactionDB.commit();
            }
        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return account;
    }

    public Account findEntityByUser(User user) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_USER)) {
            transactionDB.initTransaction(userDAO, bankDAO, typeCurrencyDAO);
            statement.setString(1, user.getLastName());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMiddleName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                BigDecimal remainder = rs.getBigDecimal("remainder");
                user = userDAO.findEntityById(rs.getInt("id_user"));
                Bank bank = bankDAO.findEntityById(rs.getInt("id_bank"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
                transactionDB.commit();
            }
        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return account;
    }

    public Account findEntityByBank(Bank bank) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ACCOUNT_BANK)) {
            transactionDB.initTransaction(userDAO, typeCurrencyDAO);
            statement.setString(1, bank.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String numberAccount = rs.getString("number_account");
                Date openingDate = rs.getDate("opening_date");
                BigDecimal remainder = rs.getBigDecimal("remainder");
                User user = userDAO.findEntityById(rs.getInt("id_user"));
                TypeCurrency currency = typeCurrencyDAO.findEntityById(rs.getInt("id_currency"));
                account = new Account(id, numberAccount, openingDate, remainder, user, bank, currency);
                transactionDB.commit();
            }
        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return account;
    }

    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_ID)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(Bank bank) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BANK)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT)) {
            preparedStatement.setString(1, account.getNumberAccount());
            preparedStatement.setTimestamp(2, new Timestamp(account.getOpeningDate().getTime()));
            preparedStatement.setBigDecimal(3, account.getRemainder());
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT)) {
            preparedStatement.setTimestamp(1, new Timestamp(account.getOpeningDate().getTime()));
            preparedStatement.setBigDecimal(2, account.getRemainder());
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_REMAINDER)) {
            preparedStatement.setBigDecimal(1, account.getRemainder());
            preparedStatement.setString(2, account.getNumberAccount());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
