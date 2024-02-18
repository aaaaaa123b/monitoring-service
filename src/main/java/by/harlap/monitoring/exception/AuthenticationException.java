package by.harlap.monitoring.exception;

/**
 * The AuthenticationException class is a specific exception type that is thrown to indicate authentication-related errors.
 * It extends the GenericHttpException class, providing a common base for handling application-specific exceptions.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
