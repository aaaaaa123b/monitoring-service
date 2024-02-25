package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.BaseRepositoryTest;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
@DisplayName("Tests for JdbcUserRepositoryTest")
public class JdbcUserRepositoryTest extends BaseRepositoryTest {

    private final UserRepository userRepository;

    @Test
    @DisplayName("Should save user successfully")
    void saveTest() {
        User requiredUser = new User(3L, "test", "test", Role.USER);

        Optional<User> actualUser = userRepository.save(requiredUser);

        assertEquals(Optional.of(requiredUser), actualUser);
    }

    @Test
    @DisplayName("Should find user by username successfully")
    void findByUsernameTest() {
        User requiredUser = new User(2L, "user", "user", Role.USER);

        User actualUser = userRepository.findByUsername("user");

        assertEquals(requiredUser, actualUser);
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void findByIdTest() {
        User requiredUser = new User(2L, "user", "user", Role.USER);
        User actualUser = userRepository.findById(2L);

        assertEquals(requiredUser, actualUser);
    }
}