package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.InitializationService;
import by.harlap.monitoring.service.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class DefaultInitializationService implements InitializationService {

    private final UserService userService;

    public DefaultInitializationService(UserService userService) {
        this.userService = userService;
    }

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

        final User existingUser = userService.findUserByUsername(username);
        if (existingUser != null) return;

        final User user = new User(username, password, Role.ADMIN);

        userService.createUser(user);
    }
}
