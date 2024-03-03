package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.starter.service.AuditableDelegate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Implementation of responsible for auditing user actions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditDelegateServiceImpl implements AuditableDelegate {

    private final AuditService auditService;

    /**
     * Executes the auditing process for a specific user action.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @param message             the message describing the action
     */
    @Override
    public void execute(ProceedingJoinPoint proceedingJoinPoint, String message) {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof User user) {
                UserEvent userEvent = new UserEvent(user.getId(), message, LocalDate.now());
                auditService.createEvent(userEvent);
                log.info(user.getUsername() + " " + userEvent.getAction());
                break;
            }
        }
    }
}
