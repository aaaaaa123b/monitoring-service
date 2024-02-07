package monitoring.repository;

import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.impl.JdbcAuditRepository;
import by.harlap.monitoring.util.ConnectionManager;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DisplayName("Tests for JdbcAuditRepositoryTest")
public class JdbcAuditRepositoryTest {

    private JdbcAuditRepository auditRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("4021")
            .withDatabaseName("monitoring_test_database")
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5434), new ExposedPort(5432)))
            ));

    @BeforeAll
    static void startContainers() {
        postgreSQLContainer.start();
        LiquibaseMigrationRunner.runMigrations("liquibase-test.properties");
    }

    @AfterAll
    static void stopContainers() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
        final ConnectionManager connectionManager = new ConnectionManager("liquibase-test.properties");
        auditRepository = new JdbcAuditRepository(connectionManager);
    }

    @Test
    @DisplayName("Should save userEvent successfully")
    void saveAuditEventTest() {
        UserEvent requiredEvent = new UserEvent(4L, 2L, "Тестовое действие", LocalDate.of(2024, 1, 1));

        Optional<UserEvent> actualEvent = auditRepository.save(requiredEvent);

        assertEquals(Optional.of(requiredEvent), actualEvent);
    }

    @Test
    @DisplayName("Should find all user events successfully")
    void findAllTest() {
        UserEvent requiredEvent = new UserEvent(1L, 2L, "Тестовое действие", LocalDate.of(2024, 1, 1));

        List<UserEvent> actual = auditRepository.findAll();

        assertTrue(actual.contains(requiredEvent));
    }
}

