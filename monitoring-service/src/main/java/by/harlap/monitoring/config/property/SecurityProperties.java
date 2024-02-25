package by.harlap.monitoring.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class represents the properties related to security configuration.
 * It is responsible for retrieving security configuration properties
 * such as secret key and token time-to-live (TTL) from the Spring application context.
 */
@Component
@Getter
public class SecurityProperties {

    @Value("${security.secretKey}")
    private String secret;

    @Value("${security.tokenTime}")
    private long ttl;
}
