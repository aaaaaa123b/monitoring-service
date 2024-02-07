package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.service.DeviceService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * The DefaultDeviceService class implements the DeviceService interface
 * and provides operations related to devices.
 */
@AllArgsConstructor
public class DefaultDeviceService implements DeviceService {

    private final DeviceRepository deviceRepository;

    /**
     * Retrieves and returns a list of all available devices from the DeviceRepository.
     *
     * @return a list of all available devices
     */
    @Override
    public List<Device> listAvailableDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Saves a new device with the given device name by creating a Device instance and delegating the save operation
     * to the underlying deviceRepository.
     *
     * @param deviceName the name of new device
     */
    @Override
    public void save(String deviceName) {
        Device device = new Device(deviceName);
        deviceRepository.save(device);
    }

    /**
     * Finds a device by its ID.
     *
     * @param deviceId the ID of the device to find
     * @return an optional containing the device if found, or an empty optional if not found
     */
    @Override
    public Optional<Device> findById(Long deviceId) {
        return deviceRepository.findById(deviceId);
    }
}
