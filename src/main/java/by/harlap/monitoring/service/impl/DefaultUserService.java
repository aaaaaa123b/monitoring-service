package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.service.UserService;

/**
 * The DefaultUserService class implements the UserService interface
 * and provides operations related to user management.
 */
public class DefaultUserService implements UserService {

    /**
     * The UserRepository used for user-related operations.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new DefaultUserService with the specified UserRepository.
     *
     * @param userRepository The UserRepository used for user-related operations.
     */
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user by saving it to the UserRepository.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds and returns a user by their username from the UserRepository.
     *
     * @param username The username of the user to be found.
     * @return The found user or null if not found.
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
