package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;

/**
 * The AuthService interface defines methods for user authentication and registration.
 */
public interface AuthService {

    /**
     * Authenticates a user with the specified username and password.
     *
     * @param username the username of the user to be authenticated
     * @param password the password of the user to be authenticated
     * @return the authenticated user
     */
    User login(String username, String password);

    /**
     * Logs out the user with the specified username.
     *
     * @param username the username of the user to be logged out
     */
    void logout(String username);

    /**
     * Registers a new user with the specified username and password.
     *
     * @param username the username of the new user to be registered
     * @param password the password of the new user to be registered
     * @return the registered user
     */
    User register(String username, String password);
}
