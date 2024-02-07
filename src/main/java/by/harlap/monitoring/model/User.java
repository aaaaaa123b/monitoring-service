package by.harlap.monitoring.model;

import by.harlap.monitoring.enumeration.Role;
import lombok.*;

/**
 * The User class represents a user in the monitoring system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    private Long id;
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
