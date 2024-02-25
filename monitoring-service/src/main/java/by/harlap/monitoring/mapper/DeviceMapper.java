package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.model.Device;
import org.mapstruct.Mapper;

/**
 * The DeviceMapper interface provides methods for mapping between Device entities and CreateDeviceDto objects.
 */
@Mapper
public interface DeviceMapper {

    /**
     * Maps a Device entity to a CreateDeviceDto object.
     *
     * @param device the Device entity to map
     * @return the corresponding DeviceResponseDto object
     */
    DeviceResponseDto toDto(Device device);

    /**
     * Maps a CreateDeviceDto object to a Device entity.
     *
     * @param createDeviceDto the CreateDeviceDto object to map
     * @return the corresponding Device entity
     */
    Device toEntity(CreateDeviceDto createDeviceDto);
}
