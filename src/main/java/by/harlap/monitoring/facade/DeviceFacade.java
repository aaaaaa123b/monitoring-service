package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.mapper.DeviceMapper;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.service.DeviceService;

/**
 * Facade for device operations.
 */
public class DeviceFacade {

    private final DeviceMapper deviceMapper;
    private final DeviceService deviceService;

    /**
     * Constructs a DeviceFacade with the specified DeviceMapper and DeviceService.
     *
     * @param deviceMapper the mapper used to map CreateDeviceDto objects to Device objects
     * @param deviceService the service used to perform device-related operations
     */
    public DeviceFacade(DeviceMapper deviceMapper,DeviceService deviceService) {
        this.deviceMapper = deviceMapper;
        this.deviceService = deviceService;
    }

    /**
     * Creates a new device based on the information provided in the CreateDeviceDto.
     *
     * @param dto the data transfer object containing information about the device to be created
     * @return DeviceResponseDto representing the created device
     */
    public DeviceResponseDto createDevice(CreateDeviceDto dto) {
        final Device device = deviceMapper.toEntity(dto);

        deviceService.save(device);

        return deviceMapper.toDto(device);
    }
}
