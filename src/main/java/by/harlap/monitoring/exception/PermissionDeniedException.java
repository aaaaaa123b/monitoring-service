package by.harlap.monitoring.exception;

/**
 * This class represents an exception indicating that permission is denied for a certain operation.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String message) {
        super(message);
    }
}
