package by.harlap.monitoring.dto.meterReadingRecord;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * The CreateMeterReadingsDto class represents a DTO (Data Transfer Object) for creating meter readings.
 * It contains information about the device values.
 */
@Getter
@Setter
public class CreateMeterReadingsDto {

    private Map<String, Double> deviceValues;
}
