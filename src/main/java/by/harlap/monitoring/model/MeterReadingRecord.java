package by.harlap.monitoring.model;

import lombok.*;

import java.time.LocalDate;

/**
 * Represents a meter reading record, storing information about a specific reading.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingRecord {

    private Long id;
    private Long userId;
    private Long deviceId;
    private Double value;
    private LocalDate date;

    public MeterReadingRecord(Long userId, Long deviceId, Double value, LocalDate date) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.value = value;
        this.date = date;
    }
}


