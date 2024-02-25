package by.harlap.monitoring.starter.condition;

import by.harlap.monitoring.starter.annotations.EnableCustomLogging;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EnableCustomLoggingCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        if (beanFactory == null) return false;

        return beanFactory.getBeansWithAnnotation(EnableCustomLogging.class).size() > 0;
    }
}
