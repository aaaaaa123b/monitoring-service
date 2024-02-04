package by.harlap.monitoring.exception;

/**
 * Custom unchecked exception for wrapping Liquibase-related exceptions.
 * Extends RuntimeException for convenient unchecked propagation.
 */
public class UncheckedLiquibaseException extends RuntimeException {

    public UncheckedLiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
