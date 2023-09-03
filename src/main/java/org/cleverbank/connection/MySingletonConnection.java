package org.cleverbank.connection;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public enum MySingletonConnection {
    INSTANCE;

    private final PGPoolingDataSource dataSource;

    private MySingletonConnection() {
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

    public Connection getConnectionDB() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
