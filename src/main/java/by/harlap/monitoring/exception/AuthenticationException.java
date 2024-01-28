package by.harlap.monitoring.exception;

/**
 * The AuthenticationException class is a specific exception type that is thrown to indicate authentication-related errors.
 * It extends the GenericException class, providing a common base for handling application-specific exceptions.
 *
 * @see by.harlap.monitoring.exception.GenericException
 */
public class AuthenticationException extends GenericException {

    /**
     * Constructs a new AuthenticationException with the specified detail message.
     *
     * @param message A String providing details about the exception.
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
