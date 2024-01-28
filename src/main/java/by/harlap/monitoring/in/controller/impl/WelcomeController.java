package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;

/**
 * The WelcomeController class represents the initial controller for user interaction.
 * It prompts the user to choose between login, registration, or exiting the application.
 * It handles the corresponding actions by redirecting to LoginController, RegisterController,
 * or exiting the application based on user input.
 */
public class WelcomeController extends AbstractController {

    /**
     * Constructs a new WelcomeController with the specified initialization data.
     *
     * @param initializationData The data needed for initializing the controller.
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

    /**
     * Handles the login action by redirecting to the LoginController and catching any generic exceptions.
     *
     * @return True if the login action was successful, false otherwise.
     */
    private boolean handleLogin() {
        return handleGenericException(
                () -> dispatcher.getController(LoginController.class).show()
        );
    }

    /**
     * Handles the registration action by redirecting to the RegisterController and catching any generic exceptions.
     *
     * @return True if the registration action was successful, false otherwise.
     */
    private boolean handleRegister() {
        return handleGenericException(
                () -> dispatcher.getController(RegisterController.class).show()
        );
    }
}
