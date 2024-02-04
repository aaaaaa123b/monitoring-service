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

    private String username;
    private String password;
    private Role role;

}
