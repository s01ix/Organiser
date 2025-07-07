package org.github.s01ix.organiser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class DBManager {

    private static final String PROPERTIESSFILEPATH = "/db.properties";
    private static final Properties props = new Properties();

    static {
        try (var in = DBManager.class.getResourceAsStream(PROPERTIESSFILEPATH)) {
            if (in == null) throw new IllegalStateException("No file " + PROPERTIESSFILEPATH);
            props.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DBManager() { }   // utility class – brak konstruktora

    public static Connection getConnection() throws SQLException {
        String url      = props.getProperty("db.url");
        String user     = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}