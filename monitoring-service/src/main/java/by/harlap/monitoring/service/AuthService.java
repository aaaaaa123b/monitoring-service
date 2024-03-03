package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;

/**
 * The AuthServiceImpl interface defines methods for user authentication and registration.
 */
public interface AuthService {

    /**
     * Authenticates a user with the specified username and password.
     *
     * @param username the username of the user to be authenticated
     * @param password the password of the user to be authenticated
     * @return the authenticated user
     */
    String login(String username, String password);

    String register(User user);
}
