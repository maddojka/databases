package ru.soroko.databases;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Pool {
    private static ComboPooledDataSource pool =
            new ComboPooledDataSource();
    // можно настраивать конфиг программно, а можно в файле properties
    /*static {
        try {
            pool.setDriverClass("org.postgresql.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }*/

    // необходим вызов метода close у объекта Connection,
    // для того, чтобы вернуть соединение в пул
    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
// дз посмотреть типы данных в postgraSql