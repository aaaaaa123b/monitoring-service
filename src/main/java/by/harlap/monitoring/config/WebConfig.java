package by.harlap.monitoring.config;

import by.harlap.monitoring.in.filter.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * This class is responsible for configuring the web layer of the application.
 * It configures Spring MVC settings, such as message converters and interceptors.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("by.harlap.monitoring")
@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    /**
     * Configures the message converters to be used by Spring MVC.
     * In this configuration, it adds a Jackson2HttpMessageConverter with an ObjectMapper builder
     * configured to indent the output.
     *
     * @param converters list of HttpMessageConverter to which new converters will be added
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().indentOutput(true);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

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
                .addPathPatterns("/*")
                .excludePathPatterns("/register", "/auth");
    }
}
