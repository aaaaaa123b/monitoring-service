package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;

/**
 * The LoginController class is responsible for handling user login functionality.
 * It prompts the user to enter a username and password, attempts to authenticate the user using the AuthService,
 * sets the active user in the application context upon successful login, and redirects to the main menu.
 * Additionally, it handles user logout upon completing the login process or encountering an error.
 */
public class LoginController extends AbstractController {

    private final AuthService authService;

    /**
     * Constructs a new LoginController with the specified initialization data and AuthService.
     *
     * @param initializationData the data needed for initializing the controller
     * @param authService        the AuthService used for user authentication and logout
     */
    public LoginController(InitializationData initializationData, AuthService authService) {
        super(initializationData);

        this.authService = authService;
    }

    /**
     * Displays prompts for the user to enter a username and password, attempts to log in the user,
     * sets the active user in the application context upon successful login,
     * displays a welcome message, redirects to the main menu, and handles user logout upon completion.
     */
    @Override
    public void show() {
        console.print("Введите имя пользователя");
        final String username = console.readLine();

        console.print("Введите пароль");
        final String password = console.readLine();

        final User user = authService.login(username, password);
        context.setActiveUser(user);

        console.print("Добрый день, " + username + "!");
        getMainMenuController(user.getRole()).show();

        context.clearActiveUser();
        authService.logout(username);
    }

    private AbstractController getMainMenuController(Role role) {
        return switch (role) {
            case ADMIN -> DependencyFactory.findController(AdminMainMenuController.class);
            case USER -> DependencyFactory.findController(UserMainMenuController.class);
            default -> throw new IllegalStateException("No controller type corresponding to specified role.");
        };
    }
}
