package monitoring.repository;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.liquibase.LiquibaseMigrationRunner;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.JdbcUserRepository;
import by.harlap.monitoring.util.ConnectionManager;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DisplayName("Tests for JdbcUserRepositoryTest")
public class JdbcUserRepositoryTest {

    private UserRepository userRepository;

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
        userRepository = new JdbcUserRepository(connectionManager);
    }

    @Test
    @DisplayName("Should save user successfully")
    void saveTest() {
        User requiredUser = new User(3L, "test", "test", Role.USER);

        Optional<User> actualUser = userRepository.save(requiredUser);

        assertEquals(Optional.of(requiredUser), actualUser);
    }

    @Test
    @DisplayName("Should find user by username successfully")
    void findByUsernameTest() {
        User requiredUser = new User(2L, "user", "user", Role.USER);

        User actualUser = userRepository.findByUsername("user");

        assertEquals(requiredUser, actualUser);
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void findByIdTest() {
        User requiredUser = new User(2L, "user", "user", Role.USER);
        User actualUser = userRepository.findById(2L);

        assertEquals(requiredUser, actualUser);
    }
}