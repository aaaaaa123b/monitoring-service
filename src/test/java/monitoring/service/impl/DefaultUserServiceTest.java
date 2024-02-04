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
    void createUser() {
        User testUser = new User("test", "test", Role.USER);

        when(userRepository.save(testUser)).thenReturn(Optional.of(testUser));

        Optional<User> actual = userService.createUser(testUser);

        assertNotNull(actual);
        assertEquals(testUser, actual);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should successfully find user by username")
    void findUserByUsername() {
        User testUser = new User("test", "test", Role.USER);

        when(userRepository.findUserByUsername(testUser.getUsername())).thenReturn(testUser);

        Optional<User> actual = userService.findUserByUsername(testUser.getUsername());

        assertNotNull(actual);
        assertEquals(testUser, actual.get());
        verify(userRepository, times(1)).findUserByUsername(testUser.getUsername());
    }

}