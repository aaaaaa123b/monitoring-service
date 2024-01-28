package by.harlap.monitoring.model;

import lombok.Getter;

import java.time.LocalDate;

/**
 * The UserEvent class represents an event associated with a user in the monitoring system.
 */
@Getter
public class UserEvent {

    private final User user;
    private final String action;
    private final LocalDate date;

    /**
     * Constructs a new UserEvent with the specified user, action, and date.
     *
     * @param user   The user associated with the event.
     * @param action The action or description of the event.
     * @param date   The date when the event occurred.
     */
    public UserEvent(User user, String action, LocalDate date) {
        this.user = user;
        this.action = action;
        this.date = date;
    }

}
