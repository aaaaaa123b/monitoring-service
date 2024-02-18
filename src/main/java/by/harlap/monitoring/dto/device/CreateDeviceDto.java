package by.harlap.monitoring.dto.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The CreateDeviceDto class represents a DTO (Data Transfer Object) for creating a device.
 * It contains information about the name of the device.
 */
@Getter
@Setter
@EqualsAndHashCode
public class CreateDeviceDto {

    private String name;
}
