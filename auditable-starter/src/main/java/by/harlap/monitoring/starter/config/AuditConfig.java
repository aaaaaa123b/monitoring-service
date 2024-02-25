package by.harlap.monitoring.starter.config;

import by.harlap.monitoring.starter.aspect.AuditableAspect;
import by.harlap.monitoring.starter.service.AuditableDelegate;
import by.harlap.monitoring.starter.service.impl.DefaultAuditableDelegate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuditableAspect auditableAspect(AuditableDelegate auditableDelegate) {
        return new AuditableAspect(auditableDelegate);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditableDelegate auditableDelegate() {
        return new DefaultAuditableDelegate();
    }
}
