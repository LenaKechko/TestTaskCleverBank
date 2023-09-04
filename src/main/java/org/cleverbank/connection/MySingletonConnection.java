package org.cleverbank.connection;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Реализация шаблона Singleton для выдачи connection с помощью перечисления
 *
 * @author Кечко Елена
 */

public enum MySingletonConnection {
    INSTANCE;

    /**
     * Поле для пула соединений
     */
    private final PGPoolingDataSource dataSource;

    /**
     * Конструктор. Содержит подключение к базе данных.
     * Данные для подключения берутся из properties-файла
     */
    MySingletonConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String serverName = resourceBundle.getString("db.serverName");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String databaseName = resourceBundle.getString("db.databaseName");
        int maxConnections = Integer.parseInt(resourceBundle.getString("db.maxConnections"));

        dataSource = new PGPoolingDataSource();
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMaxConnections(maxConnections);
    }

    /**
     * Метод выдающий connection к базе данных
     *
     * @return объект типа Connection
     * @throws RuntimeException при невозможности соединиться с БД
     */
    public Connection getConnectionDB() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
