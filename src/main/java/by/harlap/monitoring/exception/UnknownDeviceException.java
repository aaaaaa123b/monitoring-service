package by.harlap.monitoring.exception;

public class UnknownDeviceException extends GenericHttpException{

    /**
     * Constructs a new {@code GenericHttpException} with the specified HTTP status code and message.
     *
     * @param code    the HTTP status code
     * @param message the description or reason for the exception
     */
    public UnknownDeviceException(int code, String message) {
        super(code, message);
    }
}
