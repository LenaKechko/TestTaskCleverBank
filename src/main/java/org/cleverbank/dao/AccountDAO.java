package org.cleverbank.dao;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью Account и таблицей БД accounts
 *
 * @author Кечко Елена
 */

public class AccountDAO extends AbstractDAO<Integer, Account> {

    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";

    /**
     * Запрос на вывод данных по известному id
     */
    public static final String SQL_SELECT_ACCOUNT_ID = "SELECT * FROM accounts WHERE id = ?";
    /**
     * Запрос на вывод данных по известному номеру счета
     */
    public static final String SQL_SELECT_ACCOUNT_NUMBER_ACCOUNT = "SELECT * FROM accounts WHERE number_account = ?";
    /**
     * Запрос на вывод данных по известному фио пользователя
     */

    public static final String SQL_SELECT_ACCOUNT_USER = "SELECT * FROM accounts " +
            "WHERE id_user = (SELECT id FROM users WHERE lastname = ? and name = ? and middlename = ?)";

    /**
     * Запрос на вывод данных по известному наименованию банка
     */
    public static final String SQL_SELECT_ACCOUNT_BANK = "SELECT * FROM accounts " +
            "WHERE id_bank = (SELECT id FROM banks WHERE name = ?)";
    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_ACCOUNT =
            "INSERT INTO accounts(number_account, opening_date, remainder, id_user, id_bank, id_currency) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Запрос на удаление данных из таблицы БД по известному id
     */
    public static final String SQL_DELETE_ACCOUNT_ID = "DELETE FROM accounts WHERE id = ?";
    /**
     * Запрос на удаление данных из таблицы БД по известному номеру чека
     */
    public static final String SQL_DELETE_ACCOUNT_NUMBER = "DELETE FROM accounts WHERE number_account = ?";
    /**
     * Запрос на удаление данных из таблицы БД по известному id пользотеля
     */
    public static final String SQL_DELETE_ACCOUNT_USER = "DELETE FROM accounts WHERE id_user = ?";
    /**
     * Запрос на удаление данных из таблицы БД по известному id банка
     */
    public static final String SQL_DELETE_ACCOUNT_BANK = "DELETE FROM accounts WHERE id_bank = ?";
    /**
     * Запрос на изменение данных из таблицы БД по известному номеру счета
     */
    public static final String SQL_UPDATE_ACCOUNT =
            "UPDATE accounts SET opening_date = ?, remainder = ?, id_user = ?, id_bank = ?, id_currency = ? " +
                    "WHERE number_account = ?";
    /**
     * Запрос на изменение данных остатка в таблице БД по известному номеру счета
     */
    public static final String SQL_UPDATE_ACCOUNT_REMAINDER =
            "UPDATE accounts SET remainder = ? " +
                    "WHERE number_account = ?";

    /**
     * Поле для организации связи с таблицей пользователей
     */
    private UserDAO userDAO = new UserDAO();
    /**
     * Поле для организации связи с таблицей банков
     */
    private BankDAO bankDAO = new BankDAO();
    /**
     * Поле для организации связи с таблицей валют
     */
    private TypeCurrencyDAO typeCurrencyDAO = new TypeCurrencyDAO();
    /**
     * Поле для работы с транзакцией
     */
    TransactionDB transactionDB = new TransactionDB();

    /**
     * Метод для просмотра всех данных из таблицы accounts
     *
     * @return List объектов Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для нахождение сущности из БД по номеру счета
     *
     * @param numberAccount номер счета
     * @return объект Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для нахождение сущности из БД по фио пользователя
     *
     * @param user объект пользователь
     * @return объект Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для нахождение сущности из БД по наименованию банка
     *
     * @param bank объект банк
     * @return объект Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для удаления сущности из БД по id
     *
     * @param id объекта
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для удаления сущности из БД по фио пользователя
     *
     * @param user объект пользователь
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для удаления сущности из БД по наименованию банка
     *
     * @param bank объект банк
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для занесения сущности в БД
     *
     * @param account объект типа Account
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для изменения сущности в БД
     *
     * @param account объект типа Account
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
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

    /**
     * Метод для изменения остатка на счету в таблице
     *
     * @param account объект типа Account
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */

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
