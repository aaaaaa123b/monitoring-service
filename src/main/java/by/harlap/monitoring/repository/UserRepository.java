package by.harlap.monitoring.repository;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for managing user information.
 */
public interface UserRepository {

    /**
     * Finds a user by their ID in the database.
     *
     * @param id the user's ID
     * @return the user object.
     */
    User findById(Long id);

    /**
     * Saves a new user to the database.
     *
     * @param user the user to be saved
     * @return Optional containing the saved user or Optional.empty() if not saved.
     */
    Optional<User> save(User user);

    /**
     * Finds and returns a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The found user or Optional.empty() if not found.
     */
    User findUserByUsername(String username);
}
