package by.harlap.monitoring.service;

import by.harlap.monitoring.model.Device;

import java.util.List;
import java.util.Optional;

/**
 * The DeviceService interface defines methods for managing devices.
 */
public interface DeviceService {

    /**
     * Retrieves and returns a list of all available devices.
     *
     * @return a list of all available devices
     */
    List<Device> listAvailableDevices();

    /**
     * Saves a new device with the given abstract device information.
     *
     * @param abstractDevice the abstract information representing the new device
     */
    void save(String abstractDevice);

    /**
     * Retrieves an optional Device object from the repository by its unique identifier.
     *
     * @param deviceId the unique identifier of the device to find
     * @return an Optional containing the Device object if found, or an empty Optional if no device with such id exists
     */
    Optional<Device> findById(Long deviceId);
}
