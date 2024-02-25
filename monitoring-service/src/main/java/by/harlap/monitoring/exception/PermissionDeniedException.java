package by.harlap.monitoring.exception;

/**
 * This class represents an exception indicating that permission is denied for a certain operation.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
public class PermissionDeniedException extends RuntimeException{

    /**
     * Constructs a new PermissionDeniedException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public PermissionDeniedException(String message) {
        super(message);
    }
}
