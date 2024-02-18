package by.harlap.monitoring.exception;

import jakarta.servlet.http.HttpServletResponse;

/**
 * The AuthenticationException class is a specific exception type that is thrown to indicate authentication-related errors.
 * It extends the GenericHttpException class, providing a common base for handling application-specific exceptions.
 */
public class AuthenticationException extends GenericHttpException {

    /**
     * Constructs a new AuthenticationException with the specified detail message.
     *
     * @param message a String providing details about the exception
     */
    public AuthenticationException(String message) {
        super(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}
