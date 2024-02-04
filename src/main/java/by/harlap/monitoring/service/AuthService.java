package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;

/**
 * The AuthService interface defines methods for user authentication and registration.
 */
public interface AuthService {

    /**
     * Authenticates a user with the specified username and password.
     *
     * @param username The username of the user to be authenticated.
     * @param password The password of the user to be authenticated.
     * @return The authenticated user.
     */
    User login(String username, String password);

    /**
     * Logs out the user with the specified username.
     *
     * @param username The username of the user to be logged out.
     */
    void logout(String username);

    /**
     * Registers a new user with the specified username and password.
     *
     * @param username The username of the new user to be registered.
     * @param password The password of the new user to be registered.
     * @return The registered user.
     */
    User register(String username, String password);
}
