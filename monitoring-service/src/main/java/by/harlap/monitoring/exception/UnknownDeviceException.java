package by.harlap.monitoring.exception;

/**
 * This class represents an exception indicating that a device is unknown or not recognized.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
public class UnknownDeviceException extends RuntimeException {

    /**
     * Constructs a new UnknownDeviceException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public UnknownDeviceException(String message) {
        super(message);
    }
}
