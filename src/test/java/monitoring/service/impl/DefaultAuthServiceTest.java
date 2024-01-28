package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.service.impl.DefaultAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AuditService auditService;
    @InjectMocks
    private DefaultAuthService authService;

    @Test
    void testLoginSuccessful() {
        User requiredUser = new User("test", "test", Role.USER);

        when(userService.findUserByUsername("test")).thenReturn(requiredUser);

        User actualUser = authService.login("test", "test");

        assertNotNull(actualUser);
        assertEquals(requiredUser, actualUser);
        verify(auditService, times(1)).createEvent(requiredUser, "Пользователь авторизовался в системе");
    }

    @Test
    void testLoginUserNotFound() {
        when(userService.findUserByUsername("nonexistentUser")).thenReturn(null);

        assertThrows(AuthenticationException.class, () -> authService.login("nonexistentUser", "password"));
    }


    @Test
    void testLoginIncorrectPassword() {
        User testUser = new User("testUser", "correctPassword", Role.USER);
        when(userService.findUserByUsername("testUser")).thenReturn(testUser);

        assertThrows(AuthenticationException.class, () -> authService.login("testUser", "incorrectPassword"));
    }

    @Test
    void testLogout() {
        User testUser = new User("testUser", "testPassword", Role.USER);
        when(userService.findUserByUsername("testUser")).thenReturn(testUser);

        assertThrows(AuthenticationException.class, () -> authService.logout("testUser"));

        verify(auditService, times(1)).createEvent(testUser, "Пользователь вышел из системы");
    }

    @Test
    void testRegister() {
        when(userService.findUserByUsername("test")).thenReturn(null);
        User user = new User("test", "test", Role.USER);

        authService.register("test", "test");

        verify(auditService, times(1)).createEvent(user, "Пользователь зарегистрировался в системе");
    }

    @Test
    void testRegisterUserAlreadyExists() {
        User existingUser = new User("test", "test", Role.USER);
        when(userService.findUserByUsername("test")).thenReturn(existingUser);

        assertThrows(AuthenticationException.class, () -> authService.register("test", "test"));
    }
}