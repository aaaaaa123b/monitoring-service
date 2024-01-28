package by.harlap.monitoring.model;

import by.harlap.monitoring.model.base.AbstractDevice;
import lombok.EqualsAndHashCode;

/**
 * The ColdWaterDevice class represents a specific type of device, namely a cold water meter.
 * It extends the AbstractDevice class and inherits its properties and behavior.
 */
@EqualsAndHashCode(callSuper = true)
public class ColdWaterDevice extends AbstractDevice {

    /**
     * Constructs a ColdWaterDevice with the specified name.
     *
     * @param name The name of the cold water device.
     */
    public ColdWaterDevice(String name) {
        super(name);
    }
}
