package by.harlap.monitoring.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class represents the properties related to the database connection.
 * It is responsible for retrieving database connection configuration
 * properties such as URL, username, password, and driver from the Spring
 * application context.
 */
@Component
@Getter
public class DatabaseProperties {

    @Value("${database.connection.url}")
    private String url;

    @Value("${database.connection.username}")
    private String username;

    @Value("${database.connection.password}")
    private String password;

    @Value("${database.connection.driver}")
    private String driver;
}
