package by.harlap.monitoring.exception;

/**
 * Custom exception class representing the situation where an entity is not found.
 */
public class EntityNotFoundException extends GenericException {

    /**
     * Constructs a new GenericException with the specified detail message.
     *
     * @param message A String providing details about the exception.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
