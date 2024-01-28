package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.UserEvent;

import java.util.List;

/**
 * The AuditRepository interface defines methods for storing user audit events.
 */
public interface AuditRepository {

    /**
     * Saves a user audit event to the repository.
     *
     * @param event The user audit event to be saved.
     */
    void save(UserEvent event);

    /**
     * Retrieves a list of all user audit events stored in the repository.
     *
     * @return A list containing all user audit events in the repository.
     */
    List<UserEvent> findAll();
}
