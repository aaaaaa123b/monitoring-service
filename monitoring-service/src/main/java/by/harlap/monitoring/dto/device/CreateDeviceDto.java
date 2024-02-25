package by.harlap.monitoring.dto.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * The CreateDeviceDto class represents a DTO (Data Transfer Object) for creating a device.
 * It contains information about the name of the device.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceDto {

    @NotBlank(message = "Имя устройства не должно быть пустым")
    @Size(min = 3, max = 255, message = "Имя устройства должно быть от 3 до 255 символов")
    private String name;
}
