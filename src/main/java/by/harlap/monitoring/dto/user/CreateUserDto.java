package by.harlap.monitoring.dto.user;

import by.harlap.monitoring.enumeration.Role;
import lombok.Getter;
import lombok.Setter;

/**
 * The CreateUserDto class represents a DTO (Data Transfer Object) for creating users.
 * It contains information about the username, password, and role.
 */
@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String password;
    private Role role;
}
