package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;

/**
 * The UserService interface defines methods for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user by saving it.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    User createUser(User user);

    /**
     * Finds and returns a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The found user or null if not found.
     */
    User findUserByUsername(String username);
}
