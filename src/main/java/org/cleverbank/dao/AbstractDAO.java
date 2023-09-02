package org.cleverbank.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<K extends Number, T> {

    protected Connection connection;

    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract boolean update(T entity);

//    public void close(Statement statement) {
//        try {
//            if (statement != null) {
//                statement.close();
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
