package by.harlap.monitoring.in.listener;

import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * The LiquibaseMigrationListener class implements the ServletContextListener interface
 * and is responsible for running Liquibase migrations when the application context is initialized.
 */
@Component
@RequiredArgsConstructor
public class LiquibaseMigrationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final LiquibaseMigrationRunner migrationRunner;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        migrationRunner.runMigrations();
    }
}
