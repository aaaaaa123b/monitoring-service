package by.harlap.monitoring.exception;

/**
 * This class represents an exception indicating that a device is unknown or not recognized.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
public class UnknownDeviceException extends RuntimeException{

    public UnknownDeviceException(String message) {
        super(message);
    }
}
