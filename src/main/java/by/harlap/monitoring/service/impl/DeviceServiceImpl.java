package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.exception.DeviceAlreadyExistsException;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * The DeviceServiceImpl class implements the DeviceServiceImpl interface
 * and provides operations related to devices.
 */
@AllArgsConstructor
public class DeviceServiceImpl implements by.harlap.monitoring.service.DeviceService {

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
     * @param device the new device
     */
    @Override
    public Optional<Device> save(Device device) {
        if (deviceRepository.findByName(device.getName()).isPresent()) throw new DeviceAlreadyExistsException("Устройство с таким именем уже существует");
        return deviceRepository.save(device);
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

    /**
     * Finds a device by its name.
     *
     * @param deviceName the name of the device to find
     * @return an Optional containing the device with the specified name if found, or an empty Optional if not found
     */
    @Override
    public Optional<Device> findByName(String deviceName) {
        return deviceRepository.findByName(deviceName);
    }
}
