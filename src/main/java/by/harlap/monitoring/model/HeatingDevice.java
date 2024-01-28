package by.harlap.monitoring.model;

import by.harlap.monitoring.model.base.AbstractDevice;
import lombok.EqualsAndHashCode;

/**
 * The HeatingDevice class represents a specific type of device, namely a heating meter.
 * It extends the AbstractDevice class and inherits its properties and behavior.
 */
@EqualsAndHashCode(callSuper = true)
public class HeatingDevice extends AbstractDevice {

    /**
     * Constructs a HeatingDevice with the specified name.
     *
     * @param name The name of the heating device.
     */
    public HeatingDevice(String name) {
        super(name);
    }
}

