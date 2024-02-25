package by.harlap.monitoring.starter.annotations;

import by.harlap.monitoring.starter.config.LoggingConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LoggingConfig.class)
public @interface EnableCustomLogging {
}
