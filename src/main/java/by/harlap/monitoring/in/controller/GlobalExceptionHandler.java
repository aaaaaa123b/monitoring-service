package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthenticationException(DeviceAlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthenticationException(EntityNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(UncheckedLiquibaseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthenticationException(UncheckedLiquibaseException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(UnknownDeviceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthenticationException(UnknownDeviceException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthenticationException(PermissionDeniedException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(GenericHttpException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAuthenticationException(GenericHttpException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
