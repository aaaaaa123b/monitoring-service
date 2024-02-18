package by.harlap.monitoring.util;

import by.harlap.monitoring.config.property.DatabaseProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
@Component
public class ConnectionManager {

    private final DatabaseProperties databaseProperties;

    public ConnectionManager(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            Class.forName(databaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер для подключения к БД не был найден!", e);
        }
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
            connection = DriverManager.getConnection(
                    databaseProperties.getUrl(),
                    databaseProperties.getUsername(),
                    databaseProperties.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с БД: ", e);
        }

        return connection;
    }
}
