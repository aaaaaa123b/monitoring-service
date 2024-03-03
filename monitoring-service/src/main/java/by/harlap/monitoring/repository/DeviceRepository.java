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
     * @return a list of Device objects representing all available devices
     */
    List<Device> findAll();

    /**
     * Saves the provided device to the database.
     *
     * @param device the device to be saved
     * @return an optional containing the saved device with its generated ID if the operation is successful,
     *         or an empty optional if saving fails
     */

    Optional<Device> save(Device device);

    /**
     * Retrieves a device by its ID from the database.
     *
     * @param deviceId the ID of the device
     * @return an Optional containing the device, or Optional.empty() if not found
     */
    Optional<Device> findById(Long deviceId);

    Optional<Device> findByName(String deviceName);
}