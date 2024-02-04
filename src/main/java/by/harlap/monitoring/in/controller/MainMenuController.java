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
     * @param initializationData The data needed for initializing the controller.
     */
    public MainMenuController(InitializationData initializationData) {
        super(initializationData);
    }

    public abstract Role getCorrespondingRole();
}
