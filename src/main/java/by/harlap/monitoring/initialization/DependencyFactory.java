package by.harlap.monitoring.initialization;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.initialization.concrete.ControllerFactory;
import by.harlap.monitoring.initialization.concrete.RepositoryFactory;
import by.harlap.monitoring.initialization.concrete.ServiceFactory;
import by.harlap.monitoring.util.ConsoleDecorator;

/**
 * Factory class responsible for creating and managing dependencies such as repositories, services, and controllers.
 */
public class DependencyFactory {

    private final RepositoryFactory repositoryFactory;
    private final ServiceFactory serviceFactory;
    private final ControllerFactory controllerFactory;

    private DependencyFactory() {
        repositoryFactory = new RepositoryFactory();
        serviceFactory = new ServiceFactory(repositoryFactory);

        AbstractController.InitializationData initializationData = new AbstractController.InitializationData(
                new ConsoleDecorator(), new ApplicationContext()
        );
        controllerFactory = new ControllerFactory(initializationData, serviceFactory);
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
     * Finds and returns an instance of the specified controller class.
     *
     * @param controllerClass the class of the controller to be found
     * @param <T>             the type of the controller
     * @return an instance of the specified controller class
     */
    public static <T> T findController(Class<? extends T> controllerClass) {
        return getInstance().controllerFactory.getController(controllerClass);
    }

    private static final class InstanceHolder {
        private static final DependencyFactory instance = new DependencyFactory();
    }
}
