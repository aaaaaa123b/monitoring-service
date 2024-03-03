package by.harlap.monitoring.exception;

/**
 * Custom exception class representing the situation where an entity is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
