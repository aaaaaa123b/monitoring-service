package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.service.AuditService;

import java.time.LocalDate;
import java.util.List;

/**
 * The DefaultAuditService class implements the AuditService interface
 * and provides functionality to create user events and save them to the AuditRepository.
 */
public class DefaultAuditService implements AuditService {

    /**
     * The AuditRepository used for saving user events.
     */
    private final AuditRepository auditRepository;

    /**
     * Constructs a new DefaultAuditService with the specified AuditRepository.
     *
     * @param auditRepository The AuditRepository used for saving user events.
     */
    public DefaultAuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**
     * Creates a user event with the provided user, message, and the current date,
     * then saves the event to the AuditRepository.
     *
     * @param user The user associated with the event.
     * @param message The message describing the event.
     */
    @Override
    public void createEvent(User user, String message) {
        final UserEvent event = new UserEvent(user, message, LocalDate.now());
        auditRepository.save(event);
    }

    /**
     * Retrieves a list of all user events stored in the associated AuditRepository.
     *
     * @return A list containing all user events in the AuditRepository.
     */
    @Override
    public List<UserEvent> findUserEvents() {
        return auditRepository.findAll();
    }
}
