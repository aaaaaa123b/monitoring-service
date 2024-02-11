package by.harlap.monitoring.in.listener;

import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * The LiquibaseMigrationListener class implements the ServletContextListener interface
 * and is responsible for running Liquibase migrations when the application context is initialized.
 */
@WebListener
public class LiquibaseMigrationListener implements ServletContextListener {

    /**
     * Runs Liquibase migrations when the application context is initialized.
     *
     * @param sce the ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseMigrationRunner.runMigrations("liquibase.properties");
    }
}
