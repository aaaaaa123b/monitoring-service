package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.JwtUtil;
import by.harlap.monitoring.starter.annotations.Auditable;
import by.harlap.monitoring.starter.annotations.Loggable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The AuthServiceImpl class implements the AuthServiceImpl interface
 * and provides authentication and registration services for users.
 */
@Service
@RequiredArgsConstructor
@Loggable
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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

        return jwtUtil.createJWT(username, password);
    }

    /**
     * Registers a user in the system and generates a JWT token upon successful registration.
     * If the user already exists, throws an AuthenticationException.
     *
     * @param user the User object to register
     * @return the JWT token generated upon successful registration
     * @throws AuthenticationException if a user with the same username already exists or if registration fails
     */
    @Auditable("Пользователь зарегистрировался в системе")
    @Override
    public String register(User user) {
        final Optional<User> optionalUser = userService.findUserByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            throw new AuthenticationException("Пользователь с таким именем уже существует.");
        }

        userService.createUser(user).orElseThrow(() -> new AuthenticationException("Не удалось зарегестрировать пользователя"));

        return jwtUtil.createJWT(user.getUsername(), user.getPassword());
    }
}
