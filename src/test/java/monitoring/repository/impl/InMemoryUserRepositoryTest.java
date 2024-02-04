package monitoring.repository.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for InMemoryUserRepository")
class InMemoryUserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void initializeDatabase() {
        userRepository = new InMemoryUserRepository();

        final User user = new User();
        user.setUsername("default");
        user.setPassword("default");
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Test
    @DisplayName("Should successfully find user by username in the repository")
    void testFindUserByUsername() {
        final String requiredUsername = "default";

        final User requiredUser = new User();
        requiredUser.setUsername(requiredUsername);
        requiredUser.setPassword("default");
        requiredUser.setRole(Role.USER);

        final Optional<User> actualUser = userRepository.findUserByUsername(requiredUsername);

        assertEquals(requiredUser, actualUser.get());
    }

    @Test
    @DisplayName("Should successfully save a new user in the repository")
    void testSave() {
        final User requiredUser = new User();
        requiredUser.setUsername("not-default");
        requiredUser.setPassword("not-default");
        requiredUser.setRole(Role.USER);

        final User actualUser = userRepository.save(requiredUser);

        assertEquals(requiredUser, actualUser);
    }
}