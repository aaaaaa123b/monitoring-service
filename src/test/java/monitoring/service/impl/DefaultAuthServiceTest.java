package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.service.impl.DefaultAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DefaultAuthService")
class DefaultAuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private DefaultAuthService authService;

    @Test
    @DisplayName("Should login successful")
    void loginSuccessfulTest() {
        User requiredUser = new User("liquibase", "liquibase", Role.USER);

        when(userService.findUserByUsername("liquibase")).thenReturn(Optional.of(requiredUser));

        User actualUser = authService.login("liquibase", "liquibase");

        assertNotNull(actualUser);
        assertEquals(requiredUser, actualUser);
        verify(auditService, times(1)).createEvent(requiredUser, "Пользователь авторизовался в системе");
    }

    @Test
    @DisplayName("Should throw AuthenticationException when user not exist")
    void loginUserNotFoundTest() {
        when(userService.findUserByUsername("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authService.login("nonexistentUser", "password"));
    }


    @Test
    @DisplayName("Should throw AuthenticationException when login with incorrect password")
    void loginIncorrectPasswordTest() {
        User testUser = new User("testUser", "correctPassword", Role.USER);

        when(userService.findUserByUsername("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(AuthenticationException.class, () -> authService.login("testUser", "incorrectPassword"));
    }

    @Test
    @DisplayName("Should logout successful")
    void logoutTest() {
        User testUser = new User("testUser", "testPassword", Role.USER);

        when(userService.findUserByUsername("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(AuthenticationException.class, () -> authService.logout("testUser"));

        verify(auditService, times(1)).createEvent(testUser, "Пользователь вышел из системы");
    }

    @Test
    @DisplayName("Should register successful")
    void registerTest() {
        User user = new User("test", "test", Role.USER);
        when(userService.findUserByUsername("test")).thenReturn(Optional.empty());
        when(userService.createUser(user)).thenReturn(Optional.of(user));

        authService.register("test", "test");

        verify(auditService, times(1)).createEvent(user, "Пользователь зарегистрировался в системе");
    }

    @Test
    @DisplayName("Should throw AuthenticationException when user already exist")
    void registerUserAlreadyExistsTest() {
        User existingUser = new User("liquibase", "liquibase", Role.USER);

        when(userService.findUserByUsername("liquibase")).thenReturn(Optional.of(existingUser));

        assertThrows(AuthenticationException.class, () -> authService.register("liquibase", "liquibase"));
    }
}