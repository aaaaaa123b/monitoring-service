package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The AuthController class extends BaseController and is responsible for handling user authentication requests.
 */
@Controller
@RequiredArgsConstructor
public class AuthController{

    private final AuthFacade authFacade;

    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDto> doPost(@RequestBody AuthenticationUserDto dto) {
        final TokenResponseDto responseData = authFacade.createToken(dto);

        return ResponseEntity.ok(responseData);
    }
}