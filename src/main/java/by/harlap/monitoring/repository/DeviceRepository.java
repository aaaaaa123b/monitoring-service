package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.Device;

import java.util.List;

/**
 * The DeviceRepository interface defines methods for retrieving information about devices.
 */
public interface DeviceRepository {

    /**
     * Retrieves a list of all available devices.
     *
     * @return A list of Device objects representing all available devices.
     */
    List<Device> findAll();

    /**
     * Saves a new device.
     *
     * @param device The device to be saved.
     */
    void save(Device device);
}