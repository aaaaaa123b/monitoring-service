package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.AuthService;
import by.harlap.monitoring.service.UserService;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * The DefaultAuthService class implements the AuthService interface
 * and provides authentication and registration services for users.
 */
@AllArgsConstructor
public class DefaultAuthService implements AuthService {

    private final UserService userService;

    private final AuditService auditService;

    /**
     * Authenticates a user based on the provided username and password.
     * Logs an authentication event and returns the authenticated user.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password provided by the user during login.
     * @return The authenticated user.
     * @throws AuthenticationException If authentication fails due to invalid credentials.
     */
    @Override
    public User login(String username, String password) {
        final Optional<User> optionalUser = userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Пользователя с таким именем не существует.");
        }

        final User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Проверьте пароль и повторите попытку.");
        }

        auditService.createEvent(user, "Пользователь авторизовался в системе");
        return user;
    }

    /**
     * Logs out a user and logs an event indicating the user has logged out.
     *
     * @param username The username of the user logging out.
     * @throws AuthenticationException A custom exception indicating that the user has logged out.
     */
    @Override
    public void logout(String username) {
        final Optional<User> optionalUser = userService.findUserByUsername(username);

        optionalUser.ifPresent(user -> auditService.createEvent(user, "Пользователь вышел из системы"));

        throw new AuthenticationException("Вы вышли из аккаунта. Для доступа к счётчикам войдите в аккаунт повторно.");
    }

    /**
     * Registers a new user with the specified username and password.
     * Logs a registration event and returns the registered user.
     *
     * @param username The username of the user being registered.
     * @param password The password chosen by the user during registration.
     * @return The registered user.
     * @throws AuthenticationException If registration fails due to an existing username.
     */
    @Override
    public User register(String username, String password) {
        final Optional<User> optionalUser = userService.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            throw new AuthenticationException("Пользователь с таким именем уже существует.");
        }

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.USER);

        auditService.createEvent(user, "Пользователь зарегистрировался в системе");

        return userService.createUser(user);
    }
}
