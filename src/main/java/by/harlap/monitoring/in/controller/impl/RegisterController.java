package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.RegisterFacade;
import by.harlap.monitoring.util.IOUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The RegisterController class extends BaseController and is responsible for user registration.
 */
public class RegisterController extends BaseController {

    private final RegisterFacade registerFacade;

    /**
     * Constructs a RegisterController object with the specified RegisterFacade.
     *
     * @param registerFacade the RegisterFacade object to use for registration operations
     */
    public RegisterController(RegisterFacade registerFacade) {
        this.registerFacade = registerFacade;
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
        final AuthenticationUserDto dto = IOUtil.read(request, AuthenticationUserDto.class);

        final TokenResponseDto tokenResponseDto = registerFacade.register(dto);

        IOUtil.write(response, tokenResponseDto, HttpServletResponse.SC_OK);
    }
}
