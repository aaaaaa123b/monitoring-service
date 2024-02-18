package by.harlap.monitoring.exception;

import lombok.Getter;

/**
 * This class represents a generic HTTP exception.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
@Getter
public class GenericHttpException extends RuntimeException {

    public GenericHttpException(String message) {
        super(message);
    }
}
