package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.JWTDecode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * AbstractController provides common functionalities and utilities for controller classes.
 * It extends HttpServlet to handle HTTP requests and responses.
 */
public abstract class AbstractController extends HttpServlet {

    protected ObjectMapper objectMapper;
    protected UserService userService;

    /**
     * Initializes the AbstractController by setting up dependencies and configuring ObjectMapper.
     */
    @Override
    public void init() {
        userService = DependencyFactory.findService(UserService.class);
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        JWTDecode.initialize();
    }

    /**
     * Overrides the service method of HttpServlet to handle exceptions uniformly.
     *
     * @param req  HttpServletRequest object representing the request
     * @param resp HttpServletResponse object representing the response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an I/O error occurs while handling the request
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (GenericHttpException exception) {
            final ErrorResponse responseData = new ErrorResponse(exception.getMessage());
            write(resp, responseData, exception.getCode());
        }
    }

    /**
     * Reads the request body and maps it to the specified class.
     *
     * @param request      HttpServletRequest object representing the request
     * @param mappingClass Class object representing the target class for mapping
     * @param <T>          type parameter specifying the class of the mapping result
     * @return an instance of the mapped class
     * @throws IOException if an I/O error occurs while reading the request body
     */
    protected <T> T read(HttpServletRequest request, Class<? extends T> mappingClass) throws IOException {
        return objectMapper.readValue(request.getReader(), mappingClass);
    }

    /**
     * Writes the response data as JSON and sets the HTTP status code.
     *
     * @param response HttpServletResponse object representing the response
     * @param data     Object representing the response data
     * @param code     HTTP status code to be set in the response
     * @throws IOException if an I/O error occurs while writing the response
     */
    protected void write(HttpServletResponse response, Object data, int code) throws IOException {
        String result = objectMapper.writeValueAsString(data);
        response.setContentType("application/json");
        response.setStatus(code);
        response.getWriter().write(result);
    }

    /**
     * Finds the active user associated with the current request.
     *
     * @param request the HTTP servlet request
     * @return the active user
     * @throws GenericHttpException if the user is not authorized to access the resource
     */
    protected User findActiveUser(HttpServletRequest request) {
        final String username = (String) request.getAttribute("username");
        final Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new GenericHttpException(HttpServletResponse.SC_UNAUTHORIZED, "Вам необходимо авторизоваться для доступа к этому ресурсу");
        }

        return optionalUser.get();
    }

    /**
     * Validates that the given user has the required role.
     *
     * @param user the user to validate
     * @param role the required role
     * @throws GenericHttpException if the user does not have the required role
     */
    protected void validateRequiredRole(User user, Role role) {
        final Role actualRole = user.getRole();
        if (actualRole == null || !actualRole.equals(role)) {
            throw new GenericHttpException(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
        }
    }
}
