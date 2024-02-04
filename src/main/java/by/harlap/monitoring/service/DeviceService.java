package by.harlap.monitoring.service;

import by.harlap.monitoring.model.Device;

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
    List<Device> listAvailableDevices();

    /**
     * Saves a new device with the given abstract device information.
     *
     * @param abstractDevice The abstract information representing the new device.
     */
    void save(String abstractDevice);
}
