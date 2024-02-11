package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.service.*;
import by.harlap.monitoring.service.impl.*;
import by.harlap.monitoring.service.impl.AuditServiceImpl;
import by.harlap.monitoring.service.impl.AuthServiceImpl;
import by.harlap.monitoring.service.impl.DeviceServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * The ServiceFactory class manages the registration and retrieval of services used in the application.
 * It provides methods to register services and retrieve them based on their interfaces/classes.
 */
public class ServiceFactory {

    private final Map<Class<?>, Object> services = new HashMap<>();

    /**
     * Constructor for ServiceFactory. Registers various services with their corresponding implementations.
     *
     * @param repositoryFactory the factory for retrieving repository instances
     */
    public ServiceFactory(RepositoryFactory repositoryFactory) {
        final AuditRepository auditRepository = repositoryFactory.findRepository(AuditRepository.class);
        final UserRepository userRepository = repositoryFactory.findRepository(UserRepository.class);
        final MetricsRecordRepository metricsRecordRepository = repositoryFactory.findRepository(MetricsRecordRepository.class);

        final AuditService auditService = new AuditServiceImpl(auditRepository);
        final UserService userService = new UserServiceImpl(userRepository);
        final AuthService authService = new AuthServiceImpl(userService);
        final MeterReadingsService meterReadingsService = new MeterReadingsServiceImpl(metricsRecordRepository);
        final DeviceService deviceService = new DeviceServiceImpl(repositoryFactory.findRepository(DeviceRepository.class));

        services.put(AuditService.class, auditService);
        services.put(UserService.class, userService);
        services.put(AuthService.class, authService);
        services.put(MeterReadingsService.class, meterReadingsService);
        services.put(DeviceService.class, deviceService);
    }

    /**
     * Retrieves a registered service based on its interface/class.
     *
     * @param serviceClass the interface/class of the service to retrieve
     * @param <T>          the type of the service
     * @return the instance of the service, or {@code null} if not found
     */
    public <T> T findService(Class<? extends T> serviceClass) {
        return (T) services.get(serviceClass);
    }
}
