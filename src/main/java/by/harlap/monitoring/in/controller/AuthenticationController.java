package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user registration and authorization operations.
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    /**
     * Handles user authentication requests by creating an authentication token.
     *
     * @param dto the AuthenticationUserDto containing user authentication data
     * @return ResponseEntity containing the generated authentication token
     */
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDto> doAuthenticate(@RequestBody AuthenticationUserDto dto) {
        final TokenResponseDto responseData = authenticationFacade.createToken(dto);

        return ResponseEntity.ok(responseData);
    }

    /**
     * Handles requests for user registration.
     *
     * @param authenticationUserDto the AuthenticationUserDto containing user registration data
     * @return ResponseEntity containing the token response data for the registered user
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<TokenResponseDto> register(@RequestBody AuthenticationUserDto authenticationUserDto) {
        final TokenResponseDto tokenResponseDto = authenticationFacade.register(authenticationUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDto);
    }
}
