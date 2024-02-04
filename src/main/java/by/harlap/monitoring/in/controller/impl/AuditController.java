package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;

/**
 * The AuditController class extends AbstractController and is responsible for displaying user audit events.
 */
public class AuditController extends AbstractController {

    private final AuditService auditService;

    /**
     * Constructs a new AuditController with the specified InitializationData and AuditService.
     *
     * @param initializationData The InitializationData for the controller.
     * @param auditService       The AuditService used for retrieving user audit events.
     */
    public AuditController(InitializationData initializationData, AuditService auditService) {
        super(initializationData);

        this.auditService = auditService;
    }

    /**
     * Displays user audit events by iterating through the events retrieved from the AuditService
     * and printing relevant information for each event.
     */
    @Override
    public void show() {
        for (UserEvent event : auditService.findUserEvents()) {
            final String message = "%s - Пользователь c id = '%s': %s".formatted(
                    event.getDate(),
                    event.getUserId(),
                    event.getAction()
            );

            console.print(message);
        }
    }
}
