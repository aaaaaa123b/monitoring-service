package by.harlap.monitoring.exception;

/**
 * Custom exception class indicating that a device already exists.
 * Extends {@link GenericHttpException}, providing HTTP status code for conflict (409).
 */
public class DeviceAlreadyExistsException extends RuntimeException{

    /**
     * Constructs a new DeviceAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public DeviceAlreadyExistsException(String message) {
        super(message);
    }
}
