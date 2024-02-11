package by.harlap.monitoring.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The RegisterUserDto class represents a DTO (Data Transfer Object) for registering users.
 * It contains information about the username and password.
 */
@Getter
@Setter
@EqualsAndHashCode
public class RegisterUserDto {

    private String username;
    private String password;
}
