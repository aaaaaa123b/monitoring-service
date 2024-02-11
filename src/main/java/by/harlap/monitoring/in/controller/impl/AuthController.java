package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.TokenResponse;
import by.harlap.monitoring.dto.user.AuthenticateUserDto;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.service.AuthService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The AuthController class extends AbstractController and is responsible for handling user authentication requests.
 */
@WebServlet("/auth")
public class AuthController extends AbstractController {

    private AuthService authService;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        authService = DependencyFactory.findService(AuthService.class);
    }

    /**
     * Handles HTTP POST requests for user authentication.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticateUserDto dto = read(request, AuthenticateUserDto.class);

        String jwtToken = authService.login(dto.getUsername(), dto.getPassword());
        TokenResponse tokenResponse = new TokenResponse(jwtToken);

        write(response, tokenResponse, HttpServletResponse.SC_OK);
    }
}