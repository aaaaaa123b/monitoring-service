package by.harlap.monitoring.starter.aspect;

import by.harlap.monitoring.starter.annotations.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * This aspect class handles logging functionality for methods or classes annotated with {@link Loggable}.
 * It intercepts method execution and logs information about method calls and execution times.
 */
@Slf4j
@Aspect
public class LoggableAspect {

    /**
     * Pointcut definition to target methods or classes annotated with {@link Loggable}.
     */
    @Pointcut("within(@by.harlap.monitoring.starter.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {
    }

    /**
     * Intercepts method execution and logs information about method calls and execution times for methods or classes annotated with {@link Loggable}.
     *
     * @param proceedingJoinPoint the ProceedingJoinPoint representing the intercepted method execution
     * @return the result of the intercepted method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Calling method '{}'", proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;

        log.info("Execution of method '{}' finished. Execution time is {} ms.", proceedingJoinPoint.getSignature(), end);
        return result;
    }
}
