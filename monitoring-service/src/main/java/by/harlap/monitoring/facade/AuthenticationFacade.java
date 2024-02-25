package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.mapper.UserMapper;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade for user registration and authorization operations.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final AuthService authService;
    private final UserMapper userMapper;

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

    /**
     * Registers a new user using the provided authentication user DTO.
     *
     * @param dto the authentication user DTO containing user registration information.
     * @return TokenResponseDto representing the JWT token generated upon successful registration.
     */
    public TokenResponseDto register(AuthenticationUserDto dto) {
        final User user = userMapper.toEntity(dto);

        final String jwtToken = authService.register(user);

        return new TokenResponseDto(jwtToken);
    }
}
