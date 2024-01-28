package by.harlap.monitoring.model.base;

import lombok.EqualsAndHashCode;

/**
 * The AbstractDevice class represents a generic device with a name.
 * Subclasses should extend this class to define specific types of devices.
 */
@EqualsAndHashCode
public abstract class AbstractDevice {

    /** The name of the device. */
    private final String name;

    /**
     * Constructs an AbstractDevice with the specified name.
     *
     * @param name The name of the device.
     */
    public AbstractDevice(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the device.
     *
     * @return The name of the device.
     */
    public String getName() {
        return name;
    }
}
