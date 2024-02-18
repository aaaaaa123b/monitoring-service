package by.harlap.monitoring.exception;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom exception class representing the situation where an entity is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
