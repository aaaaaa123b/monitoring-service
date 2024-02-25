package by.harlap.monitoring.dto.meterReadingRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The CreateMeterReadingsDto class represents a DTO (Data Transfer Object) for creating meter readings.
 * It contains information about the device values.
 */
@Getter
@Setter
@AllArgsConstructor
public class CreateMeterReadingsDto {

    @NotBlank
    private String deviceName;

    @PositiveOrZero(message = "Значение счётчика должно быть больше либо равно нуля")
    private Double value;
}
