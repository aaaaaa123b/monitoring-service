package by.harlap.monitoring.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for managing properties.
 */
public class PropertyHolder {

    private final Properties properties;

    /**
     * Constructs a PropertyHolder with the properties loaded from the specified file.
     *
     * @param filename the name of the properties file
     */
    public PropertyHolder(String filename) {
        final Properties properties = new Properties();

        try (InputStream source = getResourceAsStream(filename)) {
            properties.load(source);
        } catch (IOException e) {
            e.printStackTrace();
            final String message = "Error while loading properties from file '%s'".formatted(filename);
            throw new RuntimeException(message, e);
        }

        this.properties = properties;
    }

    /**
     * Retrieves the value of the specified property.
     *
     * @param propertyName the name of the property to retrieve
     * @return the value of the property, or null if the property is not found
     */
    public String get(String propertyName) {
        return properties.getProperty(propertyName);
    }

    private InputStream getResourceAsStream(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }
}
