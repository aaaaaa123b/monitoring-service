package by.harlap.monitoring.starter.config;

import by.harlap.monitoring.starter.aspect.LoggableAspect;
import by.harlap.monitoring.starter.condition.EnableCustomLoggingCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@Conditional(EnableCustomLoggingCondition.class)
@EnableAspectJAutoProxy
public class LoggingConfig {

    @Bean
    public LoggableAspect loggableAspect() {
        return new LoggableAspect();
    }
}
