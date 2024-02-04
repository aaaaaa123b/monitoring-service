package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.User;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for managing user information.
 */
public interface UserRepository {

    /**
     * Saves a user in the repository.
     *
     * @param user The user to be saved in the repository.
     * @return The saved User object.
     */
    User save(User user);

    /**
     * Retrieves a user by their username from the repository.
     *
     * @param username The username of the user to be retrieved.
     * @return The User object associated with the specified username, or null if not found.
     */
    Optional<User> findUserByUsername(String username);
}
