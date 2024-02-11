package by.harlap.monitoring.exception;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom exception class indicating that a device already exists.
 * Extends {@link GenericHttpException}, providing HTTP status code for conflict (409).
 */
public class DeviceAlreadyExistsException extends GenericHttpException{

    /**
     * Constructs a new GenericException with the specified detail message.
     *
     * @param message a String providing details about the exception
     */
    public DeviceAlreadyExistsException(String message) {
        super(HttpServletResponse.SC_CONFLICT, message);
    }
}
