package by.harlap.monitoring.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 */
public class ConnectionManager {

    private static final String url;
    private static final String username;
    private static final String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер 'org.postgresql.Driver' успешно загружен!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер для подключения к БД не был найден!", e);
        }

        Properties properties = loadProperties();

        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * Establishes a connection to the database.
     *
     * @return The established database connection.
     * @throws RuntimeException if connection cannot be established.
     */
    public Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с БД: ", e);
        }

        return connection;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/liquibase.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading properties", e);
        }
        return properties;
    }

}
