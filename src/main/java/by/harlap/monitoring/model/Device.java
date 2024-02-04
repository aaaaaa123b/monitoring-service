package by.harlap.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Device class represents a generic device with a name.
 * Subclasses should extend this class to define specific types of devices.
 */
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class Device {

    private final String name;
}
