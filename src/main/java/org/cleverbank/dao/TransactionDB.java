package org.cleverbank.dao;

import org.cleverbank.ConnectorDB;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionDB {
    private Connection connection;

    public void initTransaction(AbstractDAO dao, AbstractDAO... daos) {
        if (connection == null) {
            try {
                connection = ConnectorDB.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.setConnection(connection);
        for (AbstractDAO daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
