package org.cleverbank.connection;

import org.cleverbank.dao.AbstractDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionDB {
    private Connection connection;

    public void initTransaction(AbstractDAO dao, AbstractDAO... daos) {
        if (connection == null) {
                connection = MySingletonConnection.INSTANCE.getConnectionDB();
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
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }

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
