package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;

/**
 * The RegisterController class handles the user registration process.
 * It prompts the user to enter a username and password, registers the user using the AuthService,
 * sets the active user in the application context upon successful registration,
 * displays a welcome message, redirects to the main menu, and handles user logout upon completion.
 */
public class RegisterController extends AbstractController {

    private final AuthService authService;

    /**
     * Constructs a new RegisterController with the specified initialization data and AuthService.
     *
     * @param initializationData the data needed for initializing the controller
     * @param authService        the AuthService used for user registration and logout
     */
    public RegisterController(AbstractController.InitializationData initializationData, AuthService authService) {
        super(initializationData);

        this.authService = authService;
    }

    /**
     * Handles the user registration process. Prompts the user to enter a username and password,
     * registers the user using the AuthService, sets the active user in the application context
     * upon successful registration, displays a welcome message, redirects to the main menu,
     * and handles user logout upon completion.
     */
    @Override
    public void show() {
        console.print("Введите имя пользователя");
        final String username = console.readLine();

        console.print("Введите пароль");
        final String password = console.readLine();

        final User user = authService.register(username, password);
        context.setActiveUser(user);

        console.print("Добрый день, " + username + "!");
        DependencyFactory.findController(UserMainMenuController.class).show();

        context.clearActiveUser();
        authService.logout(username);
    }
}
