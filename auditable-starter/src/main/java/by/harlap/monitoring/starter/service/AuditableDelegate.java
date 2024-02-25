package by.harlap.monitoring.starter.service;

import org.aspectj.lang.ProceedingJoinPoint;

public interface AuditableDelegate {

    void execute(ProceedingJoinPoint proceedingJoinPoint, String message);
}
