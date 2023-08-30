package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankTransactionDAO extends AbstractDAO<Integer, BankTransaction> {
    public static final String SQL_SELECT_ALL_BANK_TRANSACTIONS = "SELECT * FROM transactions";

    public static final String SQL_SELECT_BANK_TRANSACTIONS_NUMBER_CHECK =
            "SELECT * FROM transactions WHERE number_check = ?";

    public static final String SQL_SELECT_NUMBER_CHECK_BANK_TRANSACTIONS =
            "SELECT number_check FROM transactions " +
                    "WHERE transaction_date = ? and id_type_of_transaction = ? and sum = ? " +
                    "and (id_sender = ? or id_sender is null) and (id_receiver = ? or id_receiver is null)";

    public static final String SQL_SELECT_BANK_TRANSACTIONS_DATE_BETWEEN =
            "SELECT * FROM transactions WHERE transaction_date between ? and ?";

    public static final String SQL_INSERT_BANK_TRANSACTION =
            "INSERT INTO transactions(transaction_date, id_type_of_transaction, sum, id_sender, id_receiver) " +
                    "VALUES (?, ?, ?, ?, ?)";

    public static final String SQL_DELETE_BANK_TRANSACTION_NUMBER_CHECK = "DELETE FROM transactions WHERE number_check = ?";

    public static final String SQL_DELETE_BANK_TRANSACTION_DATE = "DELETE FROM transactions WHERE transaction_date = ?";

    public static final String SQL_UPDATE_BANK_TRANSACTION =
            "UPDATE transactions SET transaction_date = ?, id_type_of_transaction = ?, sum = ?, id_sender = ?, id_receiver = ? " +
                    "WHERE number_check = ?";

    TypeTransactionDAO typeTransactionDAO = new TypeTransactionDAO();
    AccountDAO accountDao = new AccountDAO();

    @Override
    public List<BankTransaction> findAll() {
        List<BankTransaction> bankTransactions = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_BANK_TRANSACTIONS);
            while (rs.next()) {
                int numberCheck = rs.getInt("number_check");
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction = typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                Double summa = rs.getDouble("sum");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransactions.add(
                        new BankTransaction(numberCheck, transactionDate, typeTransaction, summa, accountOfSender, accountOfReceiver));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankTransactions;
    }


    @Override
    public BankTransaction findEntityById(Integer numberCheck) {
        BankTransaction bankTransaction = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_TRANSACTIONS_NUMBER_CHECK)) {
            statement.setInt(1, numberCheck);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction = typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                Double summa = rs.getDouble("sum");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransaction = new BankTransaction(numberCheck, transactionDate, typeTransaction, summa, accountOfSender, accountOfReceiver);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankTransaction;
    }

    public BankTransaction findEntityByDate(Date startDate, Date finishDate) {
        BankTransaction bankTransaction = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_BANK_TRANSACTIONS_DATE_BETWEEN)) {
            statement.setDate(1, startDate);
            statement.setDate(2, finishDate);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int numberCheck = rs.getInt("number_check");
                Date transactionDate = rs.getDate("transaction_date");
                TypeTransaction typeTransaction = typeTransactionDAO.findEntityById(rs.getInt("id_type_of_transaction"));
                Double summa = rs.getDouble("sum");
                Account accountOfSender = accountDao.findEntityById(rs.getInt("id_sender"));
                Account accountOfReceiver = accountDao.findEntityById(rs.getInt("id_receiver"));
                bankTransaction = new BankTransaction(numberCheck, transactionDate, typeTransaction, summa, accountOfSender, accountOfReceiver);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bankTransaction;
    }

    public Integer findNumberCheckByBankTransaction(BankTransaction bankTransaction) {
        Integer id = 0;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_NUMBER_CHECK_BANK_TRANSACTIONS)) {
            statement.setTimestamp(1, new Timestamp(bankTransaction.getTransactionDate().getTime()));
            statement.setInt(2, bankTransaction.getType().getId());
            statement.setDouble(3, bankTransaction.getSumma());
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

    @Override
    public boolean delete(Integer numberCheck) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_TRANSACTION_NUMBER_CHECK)) {
            preparedStatement.setInt(1, numberCheck);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(BankTransaction bankTransaction) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_TRANSACTION_DATE)) {
            preparedStatement.setTimestamp(1, new Timestamp(bankTransaction.getTransactionDate().getTime()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean create(BankTransaction bankTransaction) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BANK_TRANSACTION)) {
            preparedStatement.setTimestamp(1,
                    new Timestamp(bankTransaction.getTransactionDate().getTime()));
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setDouble(3, bankTransaction.getSumma());
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

    @Override
    public boolean update(BankTransaction bankTransaction) {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_TRANSACTION)) {
            preparedStatement.setTimestamp(1, new Timestamp(bankTransaction.getTransactionDate().getTime()));
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setDouble(3, bankTransaction.getSumma());
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
