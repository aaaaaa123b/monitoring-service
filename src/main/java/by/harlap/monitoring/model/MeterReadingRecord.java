package by.harlap.monitoring.model;

import by.harlap.monitoring.model.base.AbstractDevice;

import java.time.LocalDate;
import java.util.Map;

/**
 * The MeterReadingRecord record represents a record of meter readings submitted by a user.
 *
 * @param user   The user associated with the meter readings.
 * @param values A map containing pairs of AbstractDevice and corresponding meter reading values.
 * @param date   The date when the meter readings were recorded.
 */
public record MeterReadingRecord(User user, Map<AbstractDevice, Double> values, LocalDate date) {
}

