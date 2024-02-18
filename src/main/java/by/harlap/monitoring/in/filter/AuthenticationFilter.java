package by.harlap.monitoring.in.filter;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.exception.AuthenticationException;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The AuthenticationFilter class implements the Filter interface and is responsible for authenticating incoming requests.
 */
@WebFilter(urlPatterns = {"/meterReadingsHistory", "/devices", "/relevantMeterReadings", "/meterReadingsForMonth", "/audit", "/inputMeterReadings"})
public class AuthenticationFilter implements Filter {

    private final UserService userService;

    /**
     * Constructs an AuthenticationFilter object.
     * Initializes the UserService using DependencyFactory.
     */
    public AuthenticationFilter() {
        userService = DependencyFactory.findService(UserService.class);
    }

    /**
     * Performs the authentication process for incoming requests.
     *
     * @param request  the servlet request
     * @param response the servlet response
     * @param chain    the filter chain
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            doFilterInternal(request, response, chain);
        } catch (GenericHttpException exception) {
            final ErrorResponse responseData = new ErrorResponse(exception.getMessage());
            IOUtil.write((HttpServletResponse) response, responseData, exception.getCode());
        }
    }

    private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        String jwtToken = httpRequest.getHeader("Authorization");

        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            throw new AuthenticationException("Missing or invalid JWT");
        }

        jwtToken = jwtToken.substring(7);

        DecodedJWT decodedJWT = JwtUtil.verifyJWT(jwtToken);
        if (decodedJWT == null) {
            throw new AuthenticationException("Invalid JWT");
        }

        final String username = decodedJWT.getClaim("username").asString();
        if (userService.findUserByUsername(username).isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        request.setAttribute("username", username);

        try {
            chain.doFilter(request, response);
        } catch (ServletException e) {
            throw new AuthenticationException("Authorization failed");
        }
    }
}