package org.cleverbank.connection;

import org.cleverbank.dao.AbstractDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс для управления транзакциями
 *
 * @author Кечко Елена
 */

public class TransactionDB {
    /**
     * Поле для работы с подключением к БД
     */
    private Connection connection;

    /**
     * Метод для инициализации транзакции и
     * выдачи соединения конкретным объектам
     *
     * @param dao  объект для работы с таблицей БД
     * @param daos объекты для работы с таблицей БД
     */

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

    /**
     * Метод для завершения транзакции и закрытия соединения
     */
    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        connection = null;

    }

    /**
     * Метод для фиксирования всех действий с БД
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для отмены всех действий в транзакции в случае exception
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
