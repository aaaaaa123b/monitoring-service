package by.harlap.monitoring.model;

import by.harlap.monitoring.model.base.AbstractDevice;
import lombok.EqualsAndHashCode;

/**
 * The HotWaterDevice class represents a specific type of device, namely a hot water meter.
 * It extends the AbstractDevice class and inherits its properties and behavior.
 */
@EqualsAndHashCode(callSuper = true)
public class HotWaterDevice extends AbstractDevice {

    /**
     * Constructs a HotWaterDevice with the specified name.
     *
     * @param name The name of the hot water device.
     */
    public HotWaterDevice(String name) {
        super(name);
    }
}
