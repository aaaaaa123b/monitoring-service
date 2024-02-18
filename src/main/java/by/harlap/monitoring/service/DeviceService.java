package by.harlap.monitoring.service;

import by.harlap.monitoring.model.Device;

import java.util.List;
import java.util.Optional;

/**
 * The DeviceServiceImpl interface defines methods for managing devices.
 */
public interface DeviceService {

    /**
     * Retrieves and returns a list of all available devices.
     *
     * @return a list of all available devices
     */
    List<Device> listAvailableDevices();

    /**
     * Saves the given device in the database.
     *
     * @param device the device to be saved
     * @return an Optional containing the saved device if successful, or an empty Optional if the operation fails
     */
    Optional<Device> save(Device device);

    /**
     * Retrieves an optional Device object from the repository by its unique identifier.
     *
     * @param deviceId the unique identifier of the device to find
     * @return an Optional containing the Device object if found, or an empty Optional if no device with such id exists
     */
    Optional<Device> findById(Long deviceId);

    /**
     * Finds a device by its name.
     *
     * @param deviceName the name of the device to find
     * @return an Optional containing the device with the specified name if found, or an empty Optional if not found
     */
    Optional<Device> findByName(String deviceName);
}
