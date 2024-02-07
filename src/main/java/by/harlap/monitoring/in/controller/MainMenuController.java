package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.enumeration.Role;

/**
 * The AbstractController class serves as the base class for all controllers in the application.
 * It provides common functionality, such as handling unknown actions, exiting the application,
 * and handling generic exceptions. Subclasses must implement the show() method for specific behavior.
 */
public abstract class MainMenuController extends AbstractController {

    /**
     * Constructs a new UserMainMenuController with the specified initialization data.
     *
     * @param initializationData the data needed for initializing the controller
     */
    public MainMenuController(InitializationData initializationData) {
        super(initializationData);
    }

    /**
     * Returns the corresponding role associated with this object.
     * This method should be implemented by subclasses to provide specific functionality.
     *
     * @return the corresponding role object.
     */
    public abstract Role getCorrespondingRole();
}
