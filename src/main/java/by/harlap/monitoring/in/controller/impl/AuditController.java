package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.UserService;

/**
 * The AuditController class extends AbstractController and is responsible for displaying user audit events.
 */
public class AuditController extends AbstractController {

    private final AuditService auditService;
    private final UserService userService;

    /**
     * Constructs a new AuditController with the specified InitializationData and AuditService.
     *
     * @param initializationData The InitializationData for the controller.
     * @param auditService       The AuditService used for retrieving user audit events.
     */
    public AuditController(InitializationData initializationData, AuditService auditService, UserService userService) {
        super(initializationData);

        this.auditService = auditService;
        this.userService = userService;
    }

    /**
     * Displays user audit events by iterating through the events retrieved from the AuditService
     * and printing relevant information for each event.
     */
    @Override
    public void show() {
        for (UserEvent event : auditService.findUserEvents()) {
            User user = userService.findUserById(event.getUserId());
            final String message = "%s - Пользователь '%s': %s".formatted(
                    event.getDate(),
                    user.getUsername(),
                    event.getAction()
            );

            console.print(message);
        }
    }
}
