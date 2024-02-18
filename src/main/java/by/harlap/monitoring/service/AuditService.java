package by.harlap.monitoring.service;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;

import java.util.List;

/**
 * The AuditServiceImpl interface defines methods for logging audit events.
 */
public interface AuditService {

    /**
     * Creates an audit event for the specified user with the given message.
     */
    void createEvent(UserEvent userEvent);

    /**
     * Retrieves a list of all user events logged by the AuditServiceImpl.
     *
     * @return a list containing all user events logged by the AuditServiceImpl
     */
    List<UserEvent> findAllUserEvents();
}
