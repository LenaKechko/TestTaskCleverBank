package org.cleverbank.dao;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с сущностью BankTransaction и таблицей БД transactions
 *
 * @author Кечко Елена
 */
public class BankTransactionDAO extends AbstractDAO<Integer, BankTransaction> {
    /**
     * Запрос на вывод всех данных из таблицы
     */
    public static final String SQL_SELECT_ALL_BANK_TRANSACTIONS = "SELECT * FROM transactions";
    /**
     * Запрос на вывод данных по известному номеру чека
     */
    public static final String SQL_SELECT_BANK_TRANSACTIONS_NUMBER_CHECK =
            "SELECT * FROM transactions WHERE number_check = ?";

    /**
     * Запрос на вывод номера чека по известным данным операции
     */
    public static final String SQL_SELECT_NUMBER_CHECK_BANK_TRANSACTIONS =
            "SELECT number_check FROM transactions " +
                    "WHERE transaction_date = ? and id_type_of_transaction = ? and money = ? " +
                    "and (id_sender = ? or id_sender is null) and (id_receiver = ? or id_receiver is null)";

    /**
     * Запрос на вывод операций, совершенных пользователем в определенный промежуток времени
     */
    public static final String SQL_SELECT_BANK_TRANSACTIONS_DATE_BETWEEN =
            "SELECT * FROM transactions " +
                    "WHERE (id_sender = ? or id_receiver = ?) and (transaction_date between ? and ?)" +
                    "ORDER BY transaction_date";

    /**
     * Запрос на подсчет пришедших средств пользователю в определенный промежуток времени
     */
    public static final String SQL_SELECT_COMING_MONEY =
            "SELECT sum(money) FROM transactions " +
                    "WHERE ((id_sender = ? and id_receiver is null) " +
                    "or (id_receiver = ? and id_sender is not null)) " +
                    "and (transaction_date between ? and ?)";

    /**
     * Запрос на подсчет ушедших средств пользователя в определенный промежуток времени
     */
    public static final String SQL_SELECT_LEAVING_MONEY =
            "SELECT -sum(money) FROM transactions " +
                    "WHERE ((id_receiver = ? and id_sender is null) " +
                    "or (id_sender = ? and id_receiver is not null))" +
                    "and (transaction_date between ? and ?)";
    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_BANK_TRANSACTION =
            "INSERT INTO transactions(transaction_date, id_type_of_transaction, money, id_sender, id_receiver) " +
                    "VALUES (?, ?, ?, ?, ?)";
    /**
     * Запрос на удаление данных из таблицы БД по известному номеру чека
     */
    public static final String SQL_DELETE_BANK_TRANSACTION_NUMBER_CHECK =
            "DELETE FROM transactions WHERE number_check = ?";

    /**
     * Запрос на удаление данных из таблицы БД по дате транзакции
     */
    public static final String SQL_DELETE_BANK_TRANSACTION_DATE =
            "DELETE FROM transactions WHERE transaction_date = ?";
    /**
     * Запрос на изменение данных из таблицы БД по известному номеру чека
     */
    public static final String SQL_UPDATE_BANK_TRANSACTION =
            "UPDATE transactions SET transaction_date = ?, id_type_of_transaction = ?, money = ?, id_sender = ?, id_receiver = ? " +
                    "WHERE number_check = ?";

    /**
     * Поле для организации связи с таблицей тип транзакции
     */
    TypeTransactionDAO typeTransactionDAO = new TypeTransactionDAO();
    /**
     * Поле для организации связи с таблицей счета
     */
    AccountDAO accountDao = new AccountDAO();
    /**
     * Поле для работы с транзакцией
     */
    TransactionDB transactionDB = new TransactionDB();

    /**
     * Метод для просмотра всех данных из таблицы transactions
     *
     * @return List объектов Account
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public List<BankTransaction> findAll() {
        List<BankTransaction> bankTransactions = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_BANK_TRANSACTIONS);
            transactionDB.initTransaction(typeTransactionDAO, accountDao);
            while (rs.next()) {
                int numberCheck = rs.getInt("number_check");
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction =
                        typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                BigDecimal money = rs.getBigDecimal("money");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransactions.add(
                        new BankTransaction(numberCheck, transactionDate, typeTransaction,
                                money, accountOfSender, accountOfReceiver));
                transactionDB.commit();
            }

        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return bankTransactions;
    }

    /**
     * Метод для нахождение сущности из БД по номеру чека
     *
     * @param numberCheck объекта
     * @return объект BankTransaction
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public BankTransaction findEntityById(Integer numberCheck) {
        BankTransaction bankTransaction = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_TRANSACTIONS_NUMBER_CHECK)) {
            transactionDB.initTransaction(typeTransactionDAO, accountDao);
            statement.setInt(1, numberCheck);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction =
                        typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                BigDecimal money = rs.getBigDecimal("money");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransaction = new BankTransaction(numberCheck, transactionDate, typeTransaction, money, accountOfSender, accountOfReceiver);
                transactionDB.commit();
            }

        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return bankTransaction;
    }

    /**
     * Метод для нахождение транзакци по id пользователя
     * в определенный промежуток времени
     *
     * @param startDate начало временного промежутка
     * @param finishDate конец временного промежутка
     * @return List<BankTransaction> список транзакций
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public List<BankTransaction> findEntityByDate(LocalDate startDate, LocalDate finishDate, Account account) {
        List<BankTransaction> bankTransactions = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_TRANSACTIONS_DATE_BETWEEN)) {
            transactionDB.initTransaction(typeTransactionDAO, accountDao);
            statement.setInt(1, account.getId());
            statement.setInt(2, account.getId());
            statement.setTimestamp(3,
                    Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(4,
                    Timestamp.valueOf(finishDate.plusDays(1).atStartOfDay()));

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int numberCheck = rs.getInt("number_check");
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction = typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                BigDecimal money = rs.getBigDecimal("money");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransactions.add(new BankTransaction(numberCheck, transactionDate, typeTransaction, money, accountOfSender, accountOfReceiver));
                transactionDB.commit();
            }

        } catch (SQLException e) {
            transactionDB.rollback();
            System.out.println(e.getMessage());
        } finally {
            transactionDB.endTransaction();
        }
        return bankTransactions;
    }
    /**
     * Метод для номера чека по данным транзакции
     *
     * @param bankTransaction объект
     * @return numberCheck номер чека транзакции
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public Integer findNumberCheckByBankTransaction(BankTransaction bankTransaction) {
        int id = 0;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_NUMBER_CHECK_BANK_TRANSACTIONS)) {
            statement.setTimestamp(1, new Timestamp(bankTransaction.getTransactionDate().getTime()));
            statement.setInt(2, bankTransaction.getType().getId());
            statement.setBigDecimal(3, bankTransaction.getMoney());
            if (bankTransaction.getAccountOfSender() == null) {
                statement.setNull(4, Types.BIGINT);
            } else {
                statement.setInt(4, bankTransaction.getAccountOfSender().getId());
            }
            if (bankTransaction.getAccountOfReceiver() == null) {
                statement.setNull(5, Types.BIGINT);
            } else {
                statement.setInt(5, bankTransaction.getAccountOfReceiver().getId());
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("number_check");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    /**
     * Метод для нахождение полученных денежных средств
     * по id пользователя в определенный промежуток времени
     *
     * @param account объект пользователя
     * @param startDate начало временного промежутка
     * @param finishDate конец временного промежутка
     * @return сумма денежных средств
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public BigDecimal findTotalSumComingMoney(Account account, LocalDate startDate, LocalDate finishDate) {
        BigDecimal comingMoney = BigDecimal.valueOf(0.0);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_COMING_MONEY)) {
            statement.setInt(1, account.getId());
            statement.setInt(2, account.getId());
            statement.setTimestamp(3,
                    Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(4,
                    Timestamp.valueOf(finishDate.plusDays(1).atStartOfDay()));

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                comingMoney = rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comingMoney;
    }

    /**
     * Метод для нахождение потраченных денежных средств
     * по id пользователя в определенный промежуток времени
     *
     * @param account объект пользователя
     * @param startDate начало временного промежутка
     * @param finishDate конец временного промежутка
     * @return сумма потраченных денежных средств
     * @throws SQLException если при работе с БД произошла ошибка
     */
    public BigDecimal findTotalSumLeavingMoney(Account account, LocalDate startDate, LocalDate finishDate) {
        BigDecimal leavingMoney = BigDecimal.valueOf(0.0);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_LEAVING_MONEY)) {
            statement.setInt(1, account.getId());
            statement.setInt(2, account.getId());
            statement.setTimestamp(3,
                    Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(4,
                    Timestamp.valueOf(finishDate.plusDays(1).atStartOfDay()));

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                leavingMoney = rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return leavingMoney;
    }

    /**
     * Метод для удаления сущности из БД по номеру чека
     *
     * @param numberCheck номер чека
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean delete(Integer numberCheck) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_TRANSACTION_NUMBER_CHECK)) {
            preparedStatement.setInt(1, numberCheck);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для удаления сущности из БД по данныи транзакции
     *
     * @param bankTransaction объект транзакции
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean delete(BankTransaction bankTransaction) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_TRANSACTION_DATE)) {
            preparedStatement.setTimestamp(1,
                    new Timestamp(bankTransaction.getTransactionDate().getTime()));
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
     * @param bankTransaction объект типа BankTransaction
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean create(BankTransaction bankTransaction) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BANK_TRANSACTION)) {
            preparedStatement.setTimestamp(1,
                    new Timestamp(bankTransaction.getTransactionDate().getTime()));
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setBigDecimal(3, bankTransaction.getMoney());
            if (bankTransaction.getAccountOfSender() == null) {
                preparedStatement.setNull(4, Types.BIGINT);
            } else {
                preparedStatement.setInt(4, bankTransaction.getAccountOfSender().getId());
            }
            if (bankTransaction.getAccountOfReceiver() == null) {
                preparedStatement.setNull(5, Types.BIGINT);
            } else {
                preparedStatement.setInt(5, bankTransaction.getAccountOfReceiver().getId());
            }
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
     * @param bankTransaction объект типа BankTransaction
     * @return true/false - успешное выполнение операции или нет
     * @throws SQLException если при работе с БД произошла ошибка
     */
    @Override
    public boolean update(BankTransaction bankTransaction) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_TRANSACTION)) {
            preparedStatement.setTimestamp(1, new Timestamp(bankTransaction.getTransactionDate().getTime()));
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setBigDecimal(3, bankTransaction.getMoney());
            if (bankTransaction.getAccountOfSender() == null) {
                preparedStatement.setNull(4, Types.BIGINT);
            } else {
                preparedStatement.setInt(4, bankTransaction.getAccountOfSender().getId());
            }
            if (bankTransaction.getAccountOfReceiver() == null) {
                preparedStatement.setNull(5, Types.BIGINT);
            } else {
                preparedStatement.setInt(5, bankTransaction.getAccountOfReceiver().getId());
            }
            preparedStatement.setInt(6, bankTransaction.getNumberCheck());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
