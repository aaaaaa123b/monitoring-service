package monitoring.repository.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void findUserByUsername() {
        final String requiredUsername = "default";

        final User requiredUser = new User();
        requiredUser.setUsername(requiredUsername);
        requiredUser.setPassword("default");
        requiredUser.setRole(Role.USER);

        final User actualUser = userRepository.findUserByUsername(requiredUsername);

        assertEquals(requiredUser, actualUser);
    }

    @Test
    void save() {
        final User requiredUser = new User();
        requiredUser.setUsername("not-default");
        requiredUser.setPassword("not-default");
        requiredUser.setRole(Role.USER);

        final User actualUser = userRepository.save(requiredUser);

        assertEquals(requiredUser, actualUser);
    }
}