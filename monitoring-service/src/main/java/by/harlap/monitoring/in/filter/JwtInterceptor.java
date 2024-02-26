package by.harlap.monitoring.in.filter;

import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This class represents a JWT interceptor responsible for authentication and authorization tasks.
 * It implements Spring's HandlerInterceptor interface to intercept incoming HTTP requests.
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Pre-handle method to intercept incoming HTTP requests for authentication and authorization.
     *
     * @param request  the HttpServletRequest object representing the incoming request
     * @param response the HttpServletResponse object representing the response to be sent
     * @param handler  the handler for the request
     * @return true if the request should proceed, false if it should be stopped
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            throw new AuthenticationException("Отсутствует или неверный JWT");
        }

        jwtToken = jwtToken.substring(7);

        DecodedJWT decodedJWT = jwtUtil.verifyJWT(jwtToken);
        if (decodedJWT == null) {
            throw new AuthenticationException("Неверный JWT");
        }

        final String username = decodedJWT.getClaim("username").asString();
        if (userService.findUserByUsername(username).isEmpty()) {
            throw new AuthenticationException("User не найден");
        }
        request.setAttribute("username", username);

        return true;
    }
}
