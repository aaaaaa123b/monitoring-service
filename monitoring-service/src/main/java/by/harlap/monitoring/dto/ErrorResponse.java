package by.harlap.monitoring.dto;

import java.util.List;

/**
 * The ErrorResponse record represents a response containing an error message.
 */
public record ErrorResponse(List<String> messages) {
}
