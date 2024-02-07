package by.harlap.monitoring.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 */
public class ConnectionManager {

    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructs a new ConnectionManager with the specified property source.
     *
     * @param propertySource the path to the property source containing connection parameters
     * @throws RuntimeException if the PostgreSQL driver is not found or if there is an error loading properties
     */
    public ConnectionManager(String propertySource) {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер 'org.postgresql.Driver' успешно загружен!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер для подключения к БД не был найден!", e);
        }

        Properties properties = loadProperties(propertySource);

        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * Establishes a connection to the database.
     *
     * @return the established database connection
     * @throws RuntimeException if connection cannot be established
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

    private Properties loadProperties(String propertySource) {
        Properties properties = new Properties();
        try (InputStream source = getClass().getClassLoader().getResourceAsStream(propertySource)) {
            properties.load(source);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading properties", e);
        }
        return properties;
    }
}
