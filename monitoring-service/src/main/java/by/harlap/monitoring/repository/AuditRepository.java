package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.UserEvent;

import java.util.List;
import java.util.Optional;

/**
 * The AuditRepository interface defines methods for storing user audit events.
 */
public interface AuditRepository {

    /**
     * Saves the provided user event to the database.
     *
     * @param userEvent the user event to be saved
     * @return an optional containing the saved user event with its generated ID if the operation is successful,
     *         or an empty optional if saving fails
     */
    Optional<UserEvent> save(UserEvent userEvent);

    /**
     * Retrieves a list of all user audit events stored in the repository.
     *
     * @return a list containing all user audit events in the repository
     */
    List<UserEvent> findAll();
}
