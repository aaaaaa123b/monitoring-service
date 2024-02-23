package by.harlap.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This aspect class handles logging functionality for methods or classes annotated with {@link by.harlap.monitoring.annotations.Loggable}.
 * It intercepts method execution and logs information about method calls and execution times.
 */
@Aspect
@Component
public class LoggableAspect {

    /**
     * Pointcut definition to target methods or classes annotated with {@link by.harlap.monitoring.annotations.Loggable}.
     */
    @Pointcut("within(@by.harlap.monitoring.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {
    }

    /**
     * Intercepts method execution and logs information about method calls and execution times for methods or classes annotated with {@link by.harlap.monitoring.annotations.Loggable}.
     *
     * @param proceedingJoinPoint the ProceedingJoinPoint representing the intercepted method execution
     * @return the result of the intercepted method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;

        System.out.println("Execution of method" + proceedingJoinPoint.getSignature() + " finished. Execution time is" + end + " ms.");
        return result;
    }
}
