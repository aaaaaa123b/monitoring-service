package by.harlap.monitoring.aspect;

import by.harlap.monitoring.annotations.Auditable;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;

/**
 * This aspect class handles auditing functionality for methods annotated with {@link by.harlap.monitoring.annotations.Auditable}.
 * It intercepts method execution and performs auditing based on the annotation's value.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditableAspect {

    private final AuditService auditService;

    /**
     * Intercepts method execution and performs auditing for methods annotated with {@link by.harlap.monitoring.annotations.Auditable}.
     *
     * @param proceedingJoinPoint the ProceedingJoinPoint representing the intercepted method execution
     * @return the result of the intercepted method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("execution(* * (..)) && within(@by.harlap.monitoring.annotations.Auditable *)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        Auditable auditableAspect = method.getAnnotation(Auditable.class);
        String value = auditableAspect.value();

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof User user) {
                UserEvent userEvent = new UserEvent(user.getId(), value, LocalDate.now());
                auditService.createEvent(userEvent);
                System.out.println(user.getUsername() + " " + userEvent.getAction());
                break;
            }
        }
        return result;
    }
}