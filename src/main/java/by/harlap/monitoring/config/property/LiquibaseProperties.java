package by.harlap.monitoring.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class represents the properties related to Liquibase configuration.
 * It is responsible for retrieving Liquibase configuration properties
 * such as changeLogFile and schemaName from the Spring application context.
 */
@Component
@Getter
public class LiquibaseProperties {

    @Value("${liquibase.changeLogFile}")
    private String changeLogFile;

    @Value("${liquibase.schemaName}")
    private String schemaName;
}
