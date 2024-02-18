package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade for authentication operations.
 */
@RequiredArgsConstructor
@Component
public class AuthFacade {

    private final AuthService authService;

    /**
     * Creates a JWT token for the given authentication user
     *
     * @param dto the authentication user DTO containing username and password
     * @return TokenResponseDto representing the JWT token
     */
    public TokenResponseDto createToken(AuthenticationUserDto dto) {
        final String jwtToken = authService.login(dto.getUsername(), dto.getPassword());
        return new TokenResponseDto(jwtToken);
    }
}
