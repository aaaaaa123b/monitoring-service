package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.service.impl.DefaultUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DefaultUserService")
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    @DisplayName("Should successfully save user")
    void createUserTest() {
        User expectedUser = new User(1L, "test", "test", Role.USER);

        when(userRepository.save(expectedUser)).thenReturn(Optional.of(expectedUser));

        Optional<User> actual = userService.createUser(expectedUser);

        assertNotNull(actual);
        assertEquals(Optional.of(expectedUser), actual);
    }

    @Test
    @DisplayName("Should successfully find user by username")
    void findUserByUsernameTest() {
        User expectedUser = new User(1L, "test", "test", Role.USER);

        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(expectedUser);

        Optional<User> actual = userService.findUserByUsername(expectedUser.getUsername());

        assertNotNull(actual);
        assertEquals(Optional.of(expectedUser), actual);
    }
}