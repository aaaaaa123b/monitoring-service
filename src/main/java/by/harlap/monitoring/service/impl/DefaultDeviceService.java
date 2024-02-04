package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.service.DeviceService;
import lombok.AllArgsConstructor;

import java.util.List;

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
     * @return A list of all available devices.
     */
    @Override
    public List<Device> listAvailableDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Saves a new device with the given device name by creating a Device instance and delegating the save operation
     * to the underlying deviceRepository.
     *
     * @param device The device name to be saved as a new Device.
     */
    @Override
    public void save(String device) {
        deviceRepository.save(new Device(device));
    }

}
