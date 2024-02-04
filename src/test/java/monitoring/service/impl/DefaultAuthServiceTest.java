package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.exception.EntityNotFoundException;
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
    void testLoginSuccessful() {
        User requiredUser = new User("test", "test", Role.USER);

        when(userService.findUserByUsername("test")).thenReturn(Optional.of(requiredUser));

        User actualUser = authService.login("test", "test");

        assertNotNull(actualUser);
        assertEquals(requiredUser, actualUser);
        verify(auditService, times(1)).createEvent(requiredUser, "Пользователь авторизовался в системе");
    }

    @Test
    @DisplayName("Should throw AuthenticationException when user not exist")
    void testLoginUserNotFound() {
        when(userService.findUserByUsername("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authService.login("nonexistentUser", "password"));
    }


    @Test
    @DisplayName("Should throw AuthenticationException when login with incorrect password")
    void testLoginIncorrectPassword() {
        User testUser = new User("testUser", "correctPassword", Role.USER);
        when(userService.findUserByUsername("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(AuthenticationException.class, () -> authService.login("testUser", "incorrectPassword"));
    }

    @Test
    @DisplayName("Should logout successful")
    void testLogout() {
        User testUser = new User("testUser", "testPassword", Role.USER);
        when(userService.findUserByUsername("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(AuthenticationException.class, () -> authService.logout("testUser"));

        verify(auditService, times(1)).createEvent(testUser, "Пользователь вышел из системы");
    }

    @Test
    @DisplayName("Should register successful")
    void testRegister() {
        when(userService.findUserByUsername("test")).thenReturn(Optional.empty());
        User user = new User("test", "test", Role.USER);

        authService.register("test", "test");

        verify(auditService, times(1)).createEvent(user, "Пользователь зарегистрировался в системе");
    }

    @Test
    @DisplayName("Should throw AuthenticationException when user already exist")
    void testRegisterUserAlreadyExists() {
        User existingUser = new User("test", "test", Role.USER);
        when(userService.findUserByUsername("test")).thenReturn(Optional.of(existingUser));

        assertThrows(AuthenticationException.class, () -> authService.register("test", "test"));
    }
}