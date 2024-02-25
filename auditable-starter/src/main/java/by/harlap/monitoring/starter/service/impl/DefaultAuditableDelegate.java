package by.harlap.monitoring.starter.service.impl;

import by.harlap.monitoring.starter.service.AuditableDelegate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

@Slf4j
public class DefaultAuditableDelegate implements AuditableDelegate {

    @Override
    public void execute(ProceedingJoinPoint proceedingJoinPoint, String message) {
        log.warn("Auditable delegate was applied, but has not been overridden. Provided message is {}", message);
    }
}
