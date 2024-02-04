package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.service.UserService;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * The DefaultUserService class implements the UserService interface
 * and provides operations related to user management.
 */
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new user by saving it to the UserRepository.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @Override
    public Optional<User> createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds and returns a user by their username from the UserRepository.
     *
     * @param username The username of the user to be found.
     * @return The found user or null if not found.
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }
}
