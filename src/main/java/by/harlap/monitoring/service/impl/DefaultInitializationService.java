package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.InitializationService;
import by.harlap.monitoring.service.UserService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Properties;

/**
 * DefaultInitializationService is responsible for initializing the application with a default user
 * by reading configuration properties and creating the default user if it does not already exist.
 */
@AllArgsConstructor
public class DefaultInitializationService implements InitializationService {

    private final UserService userService;

    /**
     * Creates a default user based on the properties specified in the application configuration.
     * If the default user already exists, the method does nothing.
     */
    @Override
    public void createDefaultUser() {
        final Properties properties = new Properties();
        try (final var stream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String username = properties.getProperty("application.default.user");
        final String password = properties.getProperty("application.default.password");

        if (userService.findUserByUsername(username).isPresent()) {
            return;
        }

        final User user = new User(username, password, Role.ADMIN);

        userService.createUser(user);
    }
}
