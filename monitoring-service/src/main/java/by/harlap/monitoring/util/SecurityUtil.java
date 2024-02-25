package by.harlap.monitoring.util;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.exception.PermissionDeniedException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Utility class for security-related operations.
 */
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserService userService;

    public User findActiveUser(final String username) {
        final Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException( "Вам необходимо авторизоваться для доступа к этому ресурсу");
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
    public void validateRequiredRole(User user, Role role) {
        final Role actualRole = user.getRole();
        if (actualRole == null || !actualRole.equals(role)) {
            throw new PermissionDeniedException("Доступ запрещен");
        }
    }

}
