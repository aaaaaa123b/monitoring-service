package monitoring.repository;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.repository.impl.JdbcMetricsRecordRepository;
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
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DisplayName("Tests for JdbcMetricsRecordRepositoryTest")
class JdbcMetricsRecordRepositoryTest {

    private MetricsRecordRepository metricsRecordRepository;

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
        metricsRecordRepository = new JdbcMetricsRecordRepository(connectionManager);
    }

    @Test
    @DisplayName("Should save meter reading record successfully")
    void saveTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(4L, 3L, 1L, 100.5, LocalDate.of(2023, 1, 1));

        Optional<MeterReadingRecord> actualRecord = metricsRecordRepository.save(requiredRecord);

        assertEquals(Optional.of(requiredRecord), actualRecord);
    }

    @Test
    @DisplayName("Should find all meter reading records for user successfully")
    void findAllByUserTest() {
        User user = new User(2L, "user", "user", Role.USER);
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByUser(user);

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find all meter reading records successfully")
    void findAllTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(1L, 2L, 1L, 100.5, LocalDate.of(2024, 1, 1));

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAll();

        assertTrue(actualRecord.contains(requiredRecord));
    }

    @Test
    @DisplayName("Should find all meter reading records for user in a certain month successfully")
    void findAllByUserAndMonthTest() {
        User user = new User(2L, "user", "user", Role.USER);

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByUserAndMonth(user, Month.JANUARY, Year.of(2024));

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find all meter reading records in a certain month successfully")
    void findAllByMonthTest() {
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByMonth(Month.JANUARY, Year.of(2024));

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find latest meter reading record successfully")
    void findLatestTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(1L, 2L, 1L, 100.5, LocalDate.of(2024, 1, 1));

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findLatest();

        assertTrue(actualRecord.contains(requiredRecord));
    }

    @Test
    @DisplayName("Should find latest meter reading record for user successfully")
    void findLatestForUserTest() {
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findLatestForUser(2L);

        assertThat(actualRecord).hasSize(3);
    }
}