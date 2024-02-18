package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.mapper.UserMapper;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade for user registration operations.
 */
@RequiredArgsConstructor
@Component
public class RegisterFacade {

    private final AuthService authService;
    private final UserMapper userMapper;

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
