package by.harlap.monitoring.exception;

/**
 * Custom exception class indicating that a device already exists.
 * Extends {@link GenericHttpException}, providing HTTP status code for conflict (409).
 */
public class DeviceAlreadyExistsException extends RuntimeException{

    public DeviceAlreadyExistsException(String message) {
        super(message);
    }
}
