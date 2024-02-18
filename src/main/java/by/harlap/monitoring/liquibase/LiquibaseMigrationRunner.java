package by.harlap.monitoring.liquibase;

import by.harlap.monitoring.exception.UncheckedLiquibaseException;
import by.harlap.monitoring.util.PropertyHolder;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    public static void runMigrations(final String propertySource) {
        final PropertyHolder properties = new PropertyHolder(propertySource);

        final String driver = properties.get("driver");
        final String url = properties.get("url");
        final String username = properties.get("username");
        final String password = properties.get("password");
        final String changeLogFile = properties.get("changeLogFile");
        final String liquibaseSchemaName = properties.get("liquibaseSchemaName");

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

    private static void createLiquibaseSchemaIfRequired(Connection connection, String liquibaseSchemaName) {
        try (Statement statement = connection.createStatement()) {
            final String query = "CREATE SCHEMA IF NOT EXISTS %s;".formatted(liquibaseSchemaName);
            statement.execute(query);
        } catch (SQLException e) {
            throw new UncheckedLiquibaseException("Failed to initialize liquibase schema", e);
        }
    }
}
