package by.harlap.monitoring.dto.meterReadingRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The MeterReadingResponseDto class represents a DTO (Data Transfer Object) for meter reading responses.
 * It contains information about the date, username, device name, and meter value.
 */
@Getter
@Setter
@EqualsAndHashCode
public class MeterReadingResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long userId;
    private Long deviceId;
    private Double value;
}
