package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.annotations.Auditable;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.JWTDecode;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * The AuthServiceImpl class implements the AuthServiceImpl interface
 * and provides authentication and registration services for users.
 */
@AllArgsConstructor
public class AuthServiceImpl implements by.harlap.monitoring.service.AuthService {

    private final UserService userService;

    /**
     * Authenticates a user based on the provided username and password.
     * Logs an authentication event and returns the authenticated user.
     *
     * @param username the username of the user attempting to log in
     * @param password the password provided by the user during login
     * @return the authenticated user
     * @throws AuthenticationException if authentication fails due to invalid credentials
     */
    @Override
    public String login(String username, String password) {
        final Optional<User> optionalUser = userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Пользователя с таким именем не существует");
        }

        final User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Проверьте пароль и повторите попытку");
        }

        return JWTDecode.createJWT(username, password);
    }

    @Auditable("Пользователь зарегистрировался в системе")
    @Override
    public String register(User user) {
        final Optional<User> optionalUser = userService.findUserByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            throw new AuthenticationException("Пользователь с таким именем уже существует.");
        }

        userService.createUser(user).orElseThrow(() -> new AuthenticationException("Не удалось зарегестрировать пользователя"));

        return JWTDecode.createJWT(user.getUsername(), user.getPassword());
    }
}
