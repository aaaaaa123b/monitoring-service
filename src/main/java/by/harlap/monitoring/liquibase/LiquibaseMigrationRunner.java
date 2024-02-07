package by.harlap.monitoring.liquibase;

import by.harlap.monitoring.exception.UncheckedLiquibaseException;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * The LiquibaseMigrationRunner class is responsible for running Liquibase migrations based on the provided configuration.
 * It uses the Liquibase library to manage database schema changes in a flexible and version-controlled manner.
 */
public class LiquibaseMigrationRunner {

    /**
     * Runs Liquibase migrations using the provided property source.
     *
     * @param propertySource the path to the properties file containing database connection information
     * @throws UncheckedLiquibaseException if an error occurs during migration execution
     */
    public static void runMigrations(String propertySource) {
        Properties properties = loadProperties(propertySource);

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String changeLogFile = properties.getProperty("changeLogFile");
        String liquibaseSchemaName = properties.getProperty("liquibaseSchemaName");

        try {
            Class.forName(driver);

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

                createLiquibaseSchemaIfRequired(connection, liquibaseSchemaName);

                database.setLiquibaseSchemaName(liquibaseSchemaName);
                liquibase.Liquibase liquibase = new liquibase.Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);

                liquibase.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UncheckedLiquibaseException("Migrations are failed!", e);
        }
    }

    private static Properties loadProperties(String propertySource) {
        Properties properties = new Properties();
        try (InputStream source = LiquibaseMigrationRunner.class.getClassLoader().getResourceAsStream(propertySource)) {
            properties.load(source);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading properties", e);
        }
        return properties;
    }

    private static void createLiquibaseSchemaIfRequired(Connection connection, String liquibaseSchemaName) {
        try (Statement statement = connection.createStatement()) {
            final String query = "CREATE SCHEMA IF NOT EXISTS %s;".formatted(liquibaseSchemaName);
            statement.execute(query);
        } catch (SQLException e) {
            throw new UncheckedLiquibaseException("Failed to initialize liquibase schema", e);
        }
    }
}
