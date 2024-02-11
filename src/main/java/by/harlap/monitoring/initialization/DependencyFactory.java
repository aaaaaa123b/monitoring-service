package by.harlap.monitoring.initialization;

import by.harlap.monitoring.initialization.concrete.MapperFactory;
import by.harlap.monitoring.initialization.concrete.RepositoryFactory;
import by.harlap.monitoring.initialization.concrete.ServiceFactory;

/**
 * Factory class responsible for creating and managing dependencies such as repositories, services, and controllers.
 */
public class DependencyFactory {

    private final RepositoryFactory repositoryFactory;
    private final ServiceFactory serviceFactory;
    private final MapperFactory mapperFactory;

    private DependencyFactory() {
        repositoryFactory = new RepositoryFactory();
        serviceFactory = new ServiceFactory(repositoryFactory);
        mapperFactory = new MapperFactory();
    }

    private static DependencyFactory getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Finds and returns an instance of the specified repository class.
     *
     * @param repositoryClass the class of the repository to be found
     * @param <T>             the type of the repository
     * @return an instance of the specified repository class
     */
    public static <T> T findRepository(Class<? extends T> repositoryClass) {
        return getInstance().repositoryFactory.findRepository(repositoryClass);
    }

    /**
     * Finds and returns an instance of the specified service class.
     *
     * @param serviceClass the class of the service to be found
     * @param <T>          the type of the service
     * @return an instance of the specified service class
     */
    public static <T> T findService(Class<? extends T> serviceClass) {
        return getInstance().serviceFactory.findService(serviceClass);
    }

    /**
     * Finds and returns an instance of the specified mapper class.
     *
     * @param mapperClass the class of the mapper to be found
     * @param <T>          the type of the mapper
     * @return an instance of the specified mapper class
     */
    public static <T> T findMapper(Class<? extends T> mapperClass) {
        return getInstance().mapperFactory.findMapper(mapperClass);
    }

    private static final class InstanceHolder {
        private static final DependencyFactory instance = new DependencyFactory();
    }
}
