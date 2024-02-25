package by.harlap.monitoring.exception;

import lombok.Getter;

/**
 * This class represents a generic HTTP exception.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
@Getter
public class GenericHttpException extends RuntimeException {

    /**
     * Constructs a new GenericHttpException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public GenericHttpException(String message) {
        super(message);
    }
}
