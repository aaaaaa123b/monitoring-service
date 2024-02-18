package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.RegisterFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The RegisterController class extends BaseController and is responsible for user registration.
 */
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterFacade registerFacade;

    /**
     * Handles requests for user registration.
     *
     * @param authenticationUserDto the AuthenticationUserDto containing user registration data
     * @return ResponseEntity containing the token response data for the registered user
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<TokenResponseDto> register(@RequestBody AuthenticationUserDto authenticationUserDto) {
        final TokenResponseDto tokenResponseDto = registerFacade.register(authenticationUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDto);
    }
}
