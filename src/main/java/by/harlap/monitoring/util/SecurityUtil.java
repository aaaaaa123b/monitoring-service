package by.harlap.monitoring.util;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

/**
 * Utility class for security-related operations.
 */
public final class SecurityUtil {

    private final UserService userService;

    private static final class InstanceHolder {
        private static final SecurityUtil instance = new SecurityUtil();
    }

    /**
     * Returns the instance of the SecurityUtil class. This method provides access to the singleton instance
     * of the SecurityUtil.
     *
     * @return the singleton instance of the SecurityUtil class
     */
    public static SecurityUtil getInstance() {
        return SecurityUtil.InstanceHolder.instance;
    }

    private SecurityUtil() {
        userService = DependencyFactory.findService(UserService.class);
    }

    /**
     * Finds the active user based on the username provided in the HttpServletRequest.
     *
     * @param request the HttpServletRequest containing the username attribute
     * @return the active user
     * @throws GenericHttpException if the user is not found
     */
    public static User findActiveUser(HttpServletRequest request) {
        final String username = (String) request.getAttribute("username");
        final Optional<User> optionalUser = getInstance().userService.findUserByUsername(username);
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
    public static void validateRequiredRole(User user, Role role) {
        final Role actualRole = user.getRole();
        if (actualRole == null || !actualRole.equals(role)) {
            throw new GenericHttpException(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
        }
    }

}
