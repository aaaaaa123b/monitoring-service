package by.harlap.monitoring.config;

import by.harlap.monitoring.aspect.AuditableAspect;
import by.harlap.monitoring.aspect.LoggableAspect;
import by.harlap.monitoring.service.AuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * This class represents the configuration for Aspect-Oriented Programming (AOP).
 * It is responsible for configuring and enabling AOP functionality in the application.
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

    @Bean
    public AuditableAspect auditableAspect(AuditService auditService) {
        return new AuditableAspect(auditService);
    }

    @Bean
    public LoggableAspect loggableAspect() {
        return new LoggableAspect();
    }
}
