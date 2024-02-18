package by.harlap.monitoring.dto.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The DeviceResponseDto class represents a DTO for device respons.
 * It contains information about the id and name.
 */
@Getter
@Setter
@EqualsAndHashCode
public class DeviceResponseDto {
    private Long id;
    private String name;
}
