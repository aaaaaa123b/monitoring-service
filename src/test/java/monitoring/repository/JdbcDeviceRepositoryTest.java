package monitoring.repository;

import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.impl.JdbcDeviceRepository;
import by.harlap.monitoring.util.ConnectionManager;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DisplayName("Tests for JdbcDeviceRepositoryTest")
class JdbcDeviceRepositoryTest {

    private DeviceRepository deviceRepository;

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
        deviceRepository = new JdbcDeviceRepository(connectionManager);
    }

    @Test
    @DisplayName("Should save device successfully")
    void saveTest() {
        Device requiredDevice = new Device(4L, "тестовый счётчик");

        Optional<Device> actualDevice = deviceRepository.save(requiredDevice);

        assertEquals(Optional.of(requiredDevice), actualDevice);
    }

    @Test
    @DisplayName("Should find all devices successfully")
    void findAllTest() {
        Device requiredDevice = new Device(3L, "отопление");

        List<Device> actualDevice = deviceRepository.findAll();

        assertTrue(actualDevice.contains(requiredDevice));
    }

    @Test
    @DisplayName("Should find device by id successfully")
    void findByIdTest() {
        Device requiredDevice = new Device(3L, "отопление");

        Optional<Device> actualDevice = deviceRepository.findById(3L);

        assertEquals(Optional.of(requiredDevice), actualDevice);
    }
}