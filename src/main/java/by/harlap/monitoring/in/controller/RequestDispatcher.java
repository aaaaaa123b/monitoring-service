package by.harlap.monitoring.in.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * The RequestDispatcher class manages the registration and retrieval of controllers used in request processing.
 * It provides methods to register controllers and retrieve them based on their class.
 *
 * @see by.harlap.monitoring.in.controller.AbstractController
 */
public class RequestDispatcher {

    private final Map<Class<? extends AbstractController>, AbstractController> controllers = new HashMap<>();

    /**
     * Registers a controller in the RequestDispatcher.
     *
     * @param controller The controller to be registered.
     */
    public void registerController(AbstractController controller) {
        controllers.put(controller.getClass(), controller);
    }

    /**
     * Retrieves a registered controller based on its class.
     *
     * @param controllerClass The class of the controller to retrieve.
     * @return The instance of the controller, or {@code null} if not found.
     */
    public AbstractController getController(Class<? extends AbstractController> controllerClass) {
        return controllers.get(controllerClass);
    }
}
