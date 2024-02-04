package by.harlap.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The UserEvent class represents an event associated with a user in the monitoring system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {

    private Long id;
    private Long userId;
    private String action;
    private LocalDate date;
}
