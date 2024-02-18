package by.harlap.monitoring.exception;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom exception class representing the situation where an entity is not found.
 */
public class EntityNotFoundException extends GenericHttpException {

    /**
     * Constructs a new GenericException with the specified detail message.
     *
     * @param message a String providing details about the exception
     */
    public EntityNotFoundException(String message) {
        super(HttpServletResponse.SC_CONFLICT, message);
    }
}
