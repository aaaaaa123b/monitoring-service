package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.base.AbstractDevice;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.service.DeviceService;

import java.util.List;

/**
 * The DefaultDeviceService class implements the DeviceService interface
 * and provides operations related to devices.
 */
public class DefaultDeviceService implements DeviceService {

    /**
     * The DeviceRepository used for device-related operations.
     */
    private final DeviceRepository deviceRepository;

    /**
     * Constructs a new DefaultDeviceService with the specified DeviceRepository.
     *
     * @param deviceRepository The DeviceRepository used for device-related operations.
     */
    public DefaultDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Retrieves and returns a list of all available devices from the DeviceRepository.
     *
     * @return A list of all available devices.
     */
    @Override
    public List<AbstractDevice> listAvailableDevices() {
        return deviceRepository.findAll();
    }
}
