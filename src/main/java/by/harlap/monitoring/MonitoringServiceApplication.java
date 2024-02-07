package by.harlap.monitoring;

import by.harlap.monitoring.in.controller.impl.WelcomeController;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;

/**
 * The MonitoringServiceApplication class is the entry point of application.
 */
public class MonitoringServiceApplication {

    /**
     * The main method serves as the entry point for the MonitoringServiceApplication.
     * It initializes the application by creating a default user using the InitializationService,
     * and then shows the welcome screen using the WelcomeController.
     */
    public static void main(String[] args) {
        LiquibaseMigrationRunner.runMigrations("liquibase.properties");

        DependencyFactory.findController(WelcomeController.class).show();
    }
}
