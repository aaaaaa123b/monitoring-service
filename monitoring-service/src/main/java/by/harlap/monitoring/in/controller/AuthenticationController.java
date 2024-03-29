package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthenticationFacade;
import by.harlap.monitoring.in.controller.openapi.AuthenticationOpenAPI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user registration and authorization operations.
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationOpenAPI {

    private final AuthenticationFacade authenticationFacade;

    /**
     * Handles user authentication requests by creating an authentication token.
     */
    @PostMapping(value = "/auth")
    public TokenResponseDto authorize(@RequestBody @Valid AuthenticationUserDto dto) {
        return authenticationFacade.createToken(dto);
    }

    /**
     * Handles requests for user registration.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register")
    public TokenResponseDto register(@RequestBody @Valid AuthenticationUserDto authenticationUserDto) {
        return authenticationFacade.register(authenticationUserDto);
    }
}
