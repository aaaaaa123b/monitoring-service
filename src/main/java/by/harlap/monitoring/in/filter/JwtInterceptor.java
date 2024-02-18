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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String jwtToken = request.getHeader("Authorization");

            if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                throw new AuthenticationException("Missing or invalid JWT");
            }

            jwtToken = jwtToken.substring(7);

            DecodedJWT decodedJWT = jwtUtil.verifyJWT(jwtToken);
            if (decodedJWT == null) {
                throw new AuthenticationException("Invalid JWT");
            }

            final String username = decodedJWT.getClaim("username").asString();
            if (userService.findUserByUsername(username).isEmpty()) {
                throw new AuthenticationException("User not found");
            }
            request.setAttribute("username", username);

            return true;
    }
}
