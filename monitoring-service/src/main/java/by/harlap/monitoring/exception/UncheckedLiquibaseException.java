package by.harlap.monitoring.exception;

/**
 * Custom unchecked exception for wrapping Liquibase-related exceptions.
 * Extends RuntimeException for convenient unchecked propagation.
 */
public class UncheckedLiquibaseException extends RuntimeException {

    /**
     * Constructs a new UncheckedLiquibaseException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public UncheckedLiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
