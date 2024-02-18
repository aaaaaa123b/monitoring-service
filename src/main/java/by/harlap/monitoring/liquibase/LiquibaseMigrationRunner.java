package by.harlap.monitoring.liquibase;

import by.harlap.monitoring.config.property.DatabaseProperties;
import by.harlap.monitoring.config.property.LiquibaseProperties;
import by.harlap.monitoring.exception.UncheckedLiquibaseException;
import by.harlap.monitoring.util.PropertyHolder;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The LiquibaseMigrationRunner class is responsible for running Liquibase migrations based on the provided configuration.
 * It uses the Liquibase library to manage database schema changes in a flexible and version-controlled manner.
 */
@Component
@RequiredArgsConstructor
public class LiquibaseMigrationRunner {

    private final DatabaseProperties dbProperties;
    private final LiquibaseProperties liquibaseProperties;

    /**
     * Runs Liquibase migrations using the provided property source.
     *
     * @throws UncheckedLiquibaseException if an error occurs during migration execution
     */
    public void runMigrations() {
        try {
            Class.forName(dbProperties.getDriver());

            try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword())) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

                createLiquibaseSchemaIfRequired(connection, liquibaseProperties.getSchemaName());

                database.setLiquibaseSchemaName(liquibaseProperties.getSchemaName());
                liquibase.Liquibase liquibase = new liquibase.Liquibase(liquibaseProperties.getChangeLogFile(), new ClassLoaderResourceAccessor(), database);

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
