package by.harlap.monitoring.exception;

/**
 * The GenericException class is a base exception that extends the RuntimeException class.
 * It is meant to be a general-purpose exception that can be used for various types of runtime errors.
 */
public class GenericException extends RuntimeException {

    /**
     * Constructs a new GenericException with the specified detail message.
     *
     * @param message A String providing details about the exception.
     */
    public GenericException(String message) {
        super(message);
    }
}
