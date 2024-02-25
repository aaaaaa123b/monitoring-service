package by.harlap.monitoring.config;

import by.harlap.monitoring.in.filter.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is responsible for configuring the web layer of the application.
 * It configures Spring MVC settings, such as message converters and interceptors.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    /**
     * Adds interceptors to the interceptor registry.
     * In this configuration, it adds the JwtInterceptor and specifies path patterns
     * to which the interceptor should be applied and excluded.
     *
     * @param registry interceptorRegistry to which the interceptors will be added
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/register", "/auth", "/swagger-ui/**", "/api-docs/**");
    }
}
