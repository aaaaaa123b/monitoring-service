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
     * @param user the user to be created
     * @return the created user
     */
    @Override
    public Optional<User> createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds and returns a user by their username from the UserRepository.
     *
     * @param username the username of the user to be found
     * @return the found user or null if not found
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    /**
     * Finds and retrieves a user from the repository by their unique identifier.
     *
     * @param id the unique identifier of the user to find
     * @return the user object if found, or null if no user with the specified id exists
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id);
    }
}
