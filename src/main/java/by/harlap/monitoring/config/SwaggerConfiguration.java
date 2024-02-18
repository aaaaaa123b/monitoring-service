package by.harlap.monitoring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is responsible for configuring Swagger/OpenAPI documentation for the application.
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Configures an OpenAPI bean representing the Swagger documentation for the application.
     *
     * @return an instance of OpenAPI representing the Swagger documentation
     */
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API Title")
                        .description("Your API Description")
                        .version("1.0"));
    }
}
