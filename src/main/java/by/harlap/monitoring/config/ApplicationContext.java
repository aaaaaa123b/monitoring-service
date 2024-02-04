package by.harlap.monitoring.config;

import by.harlap.monitoring.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * The ApplicationContext class manages the application-wide context, including the active user.
 * It provides methods to set, retrieve, and clear the active user in the application.
 *
 */
@Getter
@Setter
public class ApplicationContext {

    private User activeUser;

    /**
     * Clears the currently active user in the application context.
     * After calling this method, there will be no active user in the application.
     */
    public void clearActiveUser() {
        this.activeUser = null;
    }
}
