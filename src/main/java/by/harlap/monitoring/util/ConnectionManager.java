package by.harlap.monitoring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
     * @throws RuntimeException if the PostgresSQL driver is not found or if there is an error loading properties
     */
    public ConnectionManager(String propertySource) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер для подключения к БД не был найден!", e);
        }

        final PropertyHolder propertyHolder = new PropertyHolder(propertySource);

        url = propertyHolder.get("url");
        username = propertyHolder.get("username");
        password = propertyHolder.get("password");
    }

    /**
     * Establishes a connection to the database.
     *
     * @return the established database connection
     * @throws RuntimeException if connection cannot be established
     */
    public Connection getConnection() {
        final Connection connection;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с БД: ", e);
        }

        return connection;
    }
}
