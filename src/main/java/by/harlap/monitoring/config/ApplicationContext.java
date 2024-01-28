package by.harlap.monitoring.config;

import by.harlap.monitoring.model.User;

/**
 * The ApplicationContext class manages the application-wide context, including the active user.
 * It provides methods to set, retrieve, and clear the active user in the application.
 *
 */
public class ApplicationContext {

    private User activeUser;

    /**
     * Retrieves the currently active user in the application context.
     *
     * @return The active user or {@code null} if no user is currently active.
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Sets the active user in the application context.
     *
     * @param activeUser The user to set as active.
     */

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Clears the currently active user in the application context.
     * After calling this method, there will be no active user in the application.
     */
    public void clearActiveUser() {
        this.activeUser = null;
    }
}
