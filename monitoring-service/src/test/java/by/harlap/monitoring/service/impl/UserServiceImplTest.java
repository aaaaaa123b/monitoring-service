package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UserServiceImpl")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService ;

    @Test
    @DisplayName("Test should successfully save user")
    void createUserTest() {
        User expectedUser = new User(1L, "test", "test", Role.USER);

        when(userRepository.save(expectedUser)).thenReturn(Optional.of(expectedUser));

        Optional<User> actual = userService.createUser(expectedUser);

        assertNotNull(actual);
        assertEquals(Optional.of(expectedUser), actual);
    }

    @Test
    @DisplayName("Test should successfully find user by username")
    void findUserByUsernameTest() {
        User expectedUser = new User(1L, "test", "test", Role.USER);

        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(expectedUser);

        Optional<User> actual = userService.findUserByUsername(expectedUser.getUsername());

        assertNotNull(actual);
        assertEquals(Optional.of(expectedUser), actual);
    }
}