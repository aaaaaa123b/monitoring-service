package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.Device;

import java.util.List;
import java.util.Optional;

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
     * Saves a new device to the database.
     *
     * @param deviceName the name of the device
     */
    void save(String deviceName);

    /**
     * Retrieves a device by its ID from the database.
     *
     * @param deviceId the ID of the device
     * @return an Optional containing the device, or Optional.empty() if not found
     */
    Optional<Device> findById(Long deviceId);
}