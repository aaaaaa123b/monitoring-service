package by.harlap.monitoring.model;

import lombok.*;

/**
 * The Device class represents a generic device with a name.
 * Subclasses should extend this class to define specific types of devices.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long id;
    private String name;
}
