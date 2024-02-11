package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.TokenResponse;
import by.harlap.monitoring.dto.user.RegisterUserDto;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.mapper.UserMapper;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The RegisterController class extends AbstractController and is responsible for user registration.
 */
@WebServlet("/register")
public class RegisterController extends AbstractController {

    private AuthService authService;
    private UserMapper userMapper;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        userMapper = DependencyFactory.findMapper(UserMapper.class);
        authService = DependencyFactory.findService(AuthService.class);
    }

    /**
     * Handles HTTP POST requests for user registration.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegisterUserDto dto = read(request, RegisterUserDto.class);
        User user = userMapper.toEntity(dto);

        String jwtToken = authService.register(user);
        TokenResponse tokenResponse = new TokenResponse(jwtToken);

        write(response, tokenResponse, HttpServletResponse.SC_OK);
    }
}
