package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Objects;

/**
 * GlobalExceptionHandler handles exceptions globally for the entire application.
 * It provides centralized exception handling for various types of exceptions.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            UncheckedLiquibaseException.class,
            UnknownDeviceException.class,
            GenericHttpException.class,
            DeviceAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(RuntimeException exception) {
        return new ErrorResponse(List.of(exception.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        return new ErrorResponse(List.of(exception.getMessage()));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthenticationException(PermissionDeniedException exception) {
        return new ErrorResponse(List.of(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidArgumentException(MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .toList();

        return new ErrorResponse(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidArgumentException(HandlerMethodValidationException exception) {
        final List<String> errors = exception.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ErrorResponse(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerException(Exception exception) {
        log.error("Unexpected exception occurred", exception);
        return new ErrorResponse(List.of("Внутренняя ошибка сервера. Обратитесь к администратору."));
    }
}
