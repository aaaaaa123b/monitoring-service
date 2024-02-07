package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.exception.GenericException;
import by.harlap.monitoring.util.ConsoleDecorator;

/**
 * The AbstractController class serves as the base class for all controllers in the application.
 * It provides common functionality, such as handling unknown actions, exiting the application,
 * and handling generic exceptions. Subclasses must implement the show() method for specific behavior.
 */
public abstract class AbstractController {

    /**
     * The console decorator used for printing messages.
     */
    protected final ConsoleDecorator console;

    /**
     * The application context providing access to application-wide data, like current active user;
     */
    protected final ApplicationContext context;

    /**
     * Constructs a new AbstractController with the specified initialization data.
     *
     * @param initializationData the data needed for initializing the controller
     */
    public AbstractController(InitializationData initializationData) {
        this.console = initializationData.console;
        this.context = initializationData.context;
    }

    /**
     * Displays a default message indicating that the specific functionality is not implemented.
     */
    public void show() {
        console.print("Не реализован");
    }

    /**
     * Handles the case where the user selects an unknown action.
     * Prints a message and prompts the user to try again.
     */
    protected void handleUnknownAction() {
        console.print("Неизвестное действие. Повторите попытку.");
    }

    /**
     * Handles the exit action by printing a farewell message and terminating the application.
     */
    protected void handleExit() {
        console.print("До свидания!");
        System.exit(0);
    }

    /**
     * Handles a generic exception by printing the exception message and returning the success status.
     *
     * @param dangerousAction the action that may throw a GenericException
     * @return true if the action was successful, false otherwise
     */
    protected boolean handleGenericException(Runnable dangerousAction) {
        try {
            dangerousAction.run();
            return true;
        } catch (GenericException exception) {
            console.print(exception.getMessage());
            return false;
        }
    }

    /**
     * A record containing the initialization data for the controller.
     */
    public record InitializationData(ConsoleDecorator console,
                                     ApplicationContext context) {
    }
}
