package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.service.AuditService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * The DefaultAuditService class implements the AuditService interface
 * and provides functionality to create user events and save them to the AuditRepository.
 */
@AllArgsConstructor
public class DefaultAuditService implements AuditService {

    private final AuditRepository auditRepository;

    /**
     * Creates a user event with the provided user, message, and the current date,
     * then saves the event to the AuditRepository.
     *
     * @param user the user associated with the event
     * @param message the message describing the event
     */
    @Override
    public void createEvent(User user, String message) {
        UserEvent userEvent = new UserEvent(user.getId(), message,LocalDate.now());
        auditRepository.save(userEvent);
    }

    /**
     * Retrieves a list of all user events stored in the associated AuditRepository.
     *
     * @return a list containing all user events in the AuditRepository
     */
    @Override
    public List<UserEvent> findUserEvents() {
        return auditRepository.findAll();
    }
}
