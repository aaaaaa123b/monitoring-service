package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.in.controller.AbstractController;

/**
 * The WelcomeController class represents the initial controller for user interaction.
 * It prompts the user to choose between login, registration, or exiting the application.
 */
public class WelcomeController extends AbstractController {

    /**
     * Constructs a new WelcomeController with the specified initialization data.
     *
     * @param initializationData the data needed for initializing the controller
     */
    public WelcomeController(AbstractController.InitializationData initializationData) {
        super(initializationData);
    }

    /**
     * Displays the welcome menu, prompting the user to choose between login, registration,
     * or exiting the application. It handles the corresponding actions by redirecting to
     * LoginController, RegisterController, or exiting the application based on user input.
     */
    @Override
    public void show() {
        boolean finished = false;
        do {
            context.clearActiveUser();

            console.print("Выберите действие");
            console.print("1. Войти");
            console.print("2. Зарегистрироваться");
            console.print("3. Выйти из приложения");

            final int welcomePageActionCode = console.readInt();

            switch (welcomePageActionCode) {
                case 1 -> finished = handleLogin();
                case 2 -> finished = handleRegister();
                case 3 -> handleExit();
                default -> {
                }
            }
        } while (!finished);
    }

    private boolean handleLogin() {
        return handleGenericException(
                () -> DependencyFactory.findController(LoginController.class).show()
        );
    }

    private boolean handleRegister() {
        return handleGenericException(
                () -> DependencyFactory.findController(RegisterController.class).show()
        );
    }
}
