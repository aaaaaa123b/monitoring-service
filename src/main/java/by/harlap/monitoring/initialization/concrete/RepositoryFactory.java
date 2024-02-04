package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.InMemoryAuditRepository;
import by.harlap.monitoring.repository.impl.InMemoryDeviceRepository;
import by.harlap.monitoring.repository.impl.InMemoryMetricsRecordRepository;
import by.harlap.monitoring.repository.impl.InMemoryUserRepository;

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
        final AuditRepository auditRepository = new InMemoryAuditRepository();
        final UserRepository userRepository = new InMemoryUserRepository();
        final DeviceRepository deviceRepository = new InMemoryDeviceRepository();
        final MetricsRecordRepository metricsRecordRepository = new InMemoryMetricsRecordRepository();

        repositories.put(AuditRepository.class, auditRepository);
        repositories.put(UserRepository.class, userRepository);
        repositories.put(DeviceRepository.class, deviceRepository);
        repositories.put(MetricsRecordRepository.class, metricsRecordRepository);
    }

    /**
     * Retrieves a registered repository based on its interface/class.
     *
     * @param repositoryClass The interface/class of the repository to retrieve.
     * @param <T>             The type of the repository.
     * @return The instance of the repository, or {@code null} if not found.
     */
    public <T> T findRepository(Class<? extends T> repositoryClass) {
        return (T) repositories.get(repositoryClass);
    }
}
