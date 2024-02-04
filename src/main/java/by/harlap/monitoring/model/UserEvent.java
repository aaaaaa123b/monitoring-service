package by.harlap.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * The UserEvent class represents an event associated with a user in the monitoring system.
 */
@Getter
@AllArgsConstructor
public class UserEvent {

    private final User user;
    private final String action;
    private final LocalDate date;
}
