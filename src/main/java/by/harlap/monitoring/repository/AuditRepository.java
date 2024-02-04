package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.UserEvent;

import java.time.LocalDate;
import java.util.List;

/**
 * The AuditRepository interface defines methods for storing user audit events.
 */
public interface AuditRepository {

    /**
     * Saves a user audit event to the repository.
     *
     * @param userId the ID of the user
     * @param action the action performed
     * @param date   the date of the audit event
     */
    void save( Long userId,String action,LocalDate date);

    /**
     * Retrieves a list of all user audit events stored in the repository.
     *
     * @return A list containing all user audit events in the repository.
     */
    List<UserEvent> findAll();
}
