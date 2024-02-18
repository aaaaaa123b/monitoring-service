package by.harlap.monitoring.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The AuthenticationUserDto class represents a DTO (Data Transfer Object) for authenticating users.
 * It contains information about the username and password.
 */
@Getter
@Setter
@EqualsAndHashCode
public class AuthenticationUserDto {

    private String username;
    private String password;
}
