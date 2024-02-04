package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;

import java.util.List;

/**
 * The AuditService interface defines methods for logging audit events.
 */
public interface AuditService {

    /**
     * Creates an audit event for the specified user with the given message.
     *
     * @param user    The user associated with the audit event.
     * @param message The message describing the audit event.
     */
    void createEvent(User user, String message);

    /**
     * Retrieves a list of all user events logged by the AuditService.
     *
     * @return A list containing all user events logged by the AuditService.
     */
    List<UserEvent> findUserEvents();
}
