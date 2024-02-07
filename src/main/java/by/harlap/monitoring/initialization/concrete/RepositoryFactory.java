package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.*;
import by.harlap.monitoring.util.ConnectionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The RepositoryFactory class manages the registration and retrieval of repositories used for data storage.
 * It provides methods to register repositories and retrieve them based on their interfaces/classes.
 */
public class RepositoryFactory {

    private final Map<Class<?>, Object> repositories = new HashMap<>();

    /**
     * Constructor for RepositoryFactory. Registers various repositories with their corresponding implementations.
     */
    public RepositoryFactory() {
        final ConnectionManager connectionManager = new ConnectionManager("liquibase.properties");
        final AuditRepository auditRepository = new JdbcAuditRepository(connectionManager);
        final UserRepository userRepository = new JdbcUserRepository(connectionManager);
        final DeviceRepository deviceRepository = new JdbcDeviceRepository(connectionManager);
        final MetricsRecordRepository metricsRecordRepository = new JdbcMetricsRecordRepository(connectionManager);

        repositories.put(AuditRepository.class, auditRepository);
        repositories.put(UserRepository.class, userRepository);
        repositories.put(DeviceRepository.class, deviceRepository);
        repositories.put(MetricsRecordRepository.class, metricsRecordRepository);
    }

    /**
     * Retrieves a registered repository based on its interface/class.
     *
     * @param repositoryClass the interface/class of the repository to retrieve
     * @param <T>             the type of the repository
     * @return the instance of the repository, or {@code null} if not found
     */
    public <T> T findRepository(Class<? extends T> repositoryClass) {
        return (T) repositories.get(repositoryClass);
    }
}
