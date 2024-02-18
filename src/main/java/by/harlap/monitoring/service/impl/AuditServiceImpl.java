package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The AuditServiceImpl class implements the AuditServiceImpl interface
 * and provides functionality to create user events and save them to the AuditRepository.
 */
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    /**
     * Creates a user event with the provided user, message, and the current date,
     * then saves the event to the AuditRepository.
     */
    @Override
    public void createEvent(UserEvent userEvent) {
        auditRepository.save(userEvent);
    }

    /**
     * Retrieves a list of all user events stored in the associated AuditRepository.
     *
     * @return a list containing all user events in the AuditRepository
     */
    @Override
    public List<UserEvent> findAllUserEvents() {
        return auditRepository.findAll();
    }
}
