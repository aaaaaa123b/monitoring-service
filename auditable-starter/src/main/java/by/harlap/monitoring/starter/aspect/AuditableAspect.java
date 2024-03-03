package by.harlap.monitoring.starter.aspect;

import by.harlap.monitoring.starter.annotations.Auditable;
import by.harlap.monitoring.starter.service.AuditableDelegate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * This aspect class handles auditing functionality for methods annotated with {@link Auditable}.
 * It intercepts method execution and performs auditing based on the annotation's value.
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class AuditableAspect {

    private final AuditableDelegate auditableDelegate;

    @Pointcut("within(@by.harlap.monitoring.starter.annotations.Auditable *) && execution(* * (..))")
    public void annotatedByAuditable() {
    }

    /**
     * Intercepts method execution and performs auditing for methods annotated with {@link Auditable}.
     *
     * @param proceedingJoinPoint the ProceedingJoinPoint representing the intercepted method execution
     * @return the result of the intercepted method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("annotatedByAuditable()")
    public Object auditing(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        Auditable auditableAspect = method.getAnnotation(Auditable.class);
        String message = auditableAspect.value();
        auditableDelegate.execute(proceedingJoinPoint, message);

        return result;
    }
}