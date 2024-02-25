package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;

import java.util.Optional;

/**
 * The UserService interface defines methods for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user by saving it.
     *
     * @param user the user to be created
     * @return the created user
     */
    Optional<User> createUser(User user);

    /**
     * Finds and returns a user by their username.
     *
     * @param username the username of the user to be found
     * @return the found user or null if not found
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Finds and retrieves a user from the repository by their unique identifier.
     *
     * @param id the unique identifier of the user to find
     * @return the user object if found, or null if no user with the specified id exists
     */
    Optional<User> findUserById(Long id);
}
