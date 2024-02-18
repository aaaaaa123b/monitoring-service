package by.harlap.monitoring.initialization;

import by.harlap.monitoring.initialization.concrete.*;

/**
 * Factory class responsible for creating and managing dependencies such as repositories, services, and controllers.
 */
public class DependencyFactory {

    private final RepositoryFactory repositoryFactory;
    private final ServiceFactory serviceFactory;
    private final MapperFactory mapperFactory;
    private final FacadeFactory facadeFactory;
    private final ValidatorFactory validatorFactory;

    private static final class InstanceHolder {
        private static final DependencyFactory instance = new DependencyFactory();
    }

    /**
     * Returns the instance of the DependencyFactory class. This method provides access to the singleton instance
     * of the DependencyFactory.
     *
     * @return the singleton instance of the DependencyFactory class
     */
    public static DependencyFactory getInstance() {
        return InstanceHolder.instance;
    }

    private DependencyFactory() {
        repositoryFactory = new RepositoryFactory();
        serviceFactory = new ServiceFactory(repositoryFactory);
        mapperFactory = new MapperFactory();
        validatorFactory = new ValidatorFactory(serviceFactory);
        facadeFactory = new FacadeFactory(mapperFactory, serviceFactory, validatorFactory);
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
     * @param <T>         the type of the mapper
     * @return an instance of the specified mapper class
     */
    public static <T> T findMapper(Class<? extends T> mapperClass) {
        return getInstance().mapperFactory.findMapper(mapperClass);
    }

    public static <T> T findFacade(Class<? extends T> facadeClass) {
        return getInstance().facadeFactory.findFacade(facadeClass);
    }

    public static <T> T findValidator(Class<? extends T> validatorClass) {
        return getInstance().validatorFactory.findValidator(validatorClass);
    }
}
