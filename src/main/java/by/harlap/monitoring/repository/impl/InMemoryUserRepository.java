package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * The InMemoryUserRepository class provides an in-memory implementation of the UserRepository interface.
 * It stores user entities in a map using usernames as keys.
 */
public class InMemoryUserRepository implements UserRepository {

    /**
     * The map to store user entities in memory.
     */
    private final Map<String, User> users = new HashMap<>();

    /**
     * Saves a user entity to the repository. If a user with the same username already exists, it is updated.
     *
     * @param user The user entity to be saved or updated.
     * @return The saved or updated user entity.
     */
    @Override
    public User save(User user) {
        users.put(user.getUsername(), user);
        return user;
    }

    /**
     * Finds and returns a user entity by username.
     *
     * @param username The username of the user to be retrieved.
     * @return The user entity with the specified username, or null if not found.
     */
    @Override
    public User findUserByUsername(String username) {
        return users.get(username);
    }
}
