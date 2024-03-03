package by.harlap.monitoring.dto.device;

import lombok.*;

/**
 * The DeviceResponseDto class represents a DTO for device respons.
 * It contains information about the id and name.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponseDto {
    private Long id;
    private String name;
}
