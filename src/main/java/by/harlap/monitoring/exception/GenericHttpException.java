package by.harlap.monitoring.exception;

import lombok.Getter;

/**
 * Represents a generic HTTP exception with a specific code and message.
 * This exception extends {@code RuntimeException}.
 * <p>
 * The {@code code} attribute represents the HTTP status code of the exception.
 * The {@code message} attribute represents the description or reason for the exception.
 */
@Getter
public class GenericHttpException extends RuntimeException {

    private final int code;
    private final String message;

    /**
     * Constructs a new {@code GenericHttpException} with the specified HTTP status code and message.
     *
     * @param code    the HTTP status code
     * @param message the description or reason for the exception
     */
    public GenericHttpException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
