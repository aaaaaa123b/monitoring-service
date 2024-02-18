package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthFacade;
import by.harlap.monitoring.util.IOUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The AuthController class extends BaseController and is responsible for handling user authentication requests.
 */

public class AuthController extends BaseController {

    private final AuthFacade authFacade;

    /**
     * Constructs an AuthController object with the specified AuthFacade.
     *
     * @param authFacade the AuthFacade object to use for authentication operations
     */
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
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
        final AuthenticationUserDto dto = IOUtil.read(request, AuthenticationUserDto.class);

        final TokenResponseDto responseData = authFacade.createToken(dto);

        IOUtil.write(response, responseData, HttpServletResponse.SC_OK);
    }
}