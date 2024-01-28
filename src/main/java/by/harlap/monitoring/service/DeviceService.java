package by.harlap.monitoring.service;

import by.harlap.monitoring.model.base.AbstractDevice;

import java.util.List;

/**
 * The DeviceService interface defines methods for managing devices.
 */
public interface DeviceService {

    /**
     * Retrieves and returns a list of all available devices.
     *
     * @return A list of all available devices.
     */
    List<AbstractDevice> listAvailableDevices();
}
