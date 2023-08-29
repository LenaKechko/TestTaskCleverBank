package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;
import org.cleverbank.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankTransactionDAO extends AbstractDAO<Integer, BankTransaction> {
    public static final String SQL_SELECT_ALL_BANK_TRANSACTIONS = "SELECT * FROM transactions";

    public static final String SQL_SELECT_BANK_TRANSACTIONS_NUMBER_CHECK = "SELECT * FROM transactions WHERE number_check = ?";

    public static final String SQL_SELECT_BANK_TRANSACTIONS_DATE_BETWEEN = "SELECT * FROM transactions WHERE transaction_date between ? and ?";

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
            preparedStatement.setDate(1, (Date) bankTransaction.getTransactionDate());
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
            preparedStatement.setDate(1, (Date) bankTransaction.getTransactionDate());
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setDouble(3, bankTransaction.getSumma());
            preparedStatement.setInt(4, bankTransaction.getAccountOfSender().getId());
            preparedStatement.setInt(5, bankTransaction.getAccountOfReceiver().getId());
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
            preparedStatement.setDate(1, (Date) bankTransaction.getTransactionDate());
            preparedStatement.setInt(2, bankTransaction.getType().getId());
            preparedStatement.setDouble(3, bankTransaction.getSumma());
            preparedStatement.setInt(4, bankTransaction.getAccountOfSender().getId());
            preparedStatement.setInt(5, bankTransaction.getAccountOfReceiver().getId());
            preparedStatement.setInt(6, bankTransaction.getNumberCheck());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
