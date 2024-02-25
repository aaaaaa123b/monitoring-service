package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthenticationFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Register and authorize")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    /**
     * Handles user authentication requests by creating an authentication token.
     *
     * @param dto the AuthenticationUserDto containing user authentication data
     * @return ResponseEntity containing the generated authentication token
     */
    @PostMapping(value = "/auth")
    public TokenResponseDto authenticate(@RequestBody @Valid AuthenticationUserDto dto) {
        return authenticationFacade.createToken(dto);
    }

    /**
     * Handles requests for user registration.
     *
     * @param authenticationUserDto the AuthenticationUserDto containing user registration data
     * @return ResponseEntity containing the token response data for the registered user
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register")
    protected TokenResponseDto register(@RequestBody @Valid AuthenticationUserDto authenticationUserDto) {
        return authenticationFacade.register(authenticationUserDto);
    }
}
