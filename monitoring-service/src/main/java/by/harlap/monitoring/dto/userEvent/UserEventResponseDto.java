package by.harlap.monitoring.dto.userEvent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

/**
 * The UserEventResponseDto class represents a DTO (Data Transfer Object) for user events.
 * It contains information about the date, username, and action.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Long userId;

    private String action;
}
