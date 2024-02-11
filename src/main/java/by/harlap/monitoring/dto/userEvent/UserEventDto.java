package by.harlap.monitoring.dto.userEvent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The UserEventDto class represents a DTO (Data Transfer Object) for user events.
 * It contains information about the date, user name, and action.
 */
@Getter
@Setter
@EqualsAndHashCode
public class UserEventDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String userName;

    private String action;
}
