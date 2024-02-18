package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

/**
 * The MeterReadingRecordMapper interface provides methods for mapping between MeterReadingRecord entities and MeterReadingResponseDto objects.
 */
@Mapper
public interface MeterReadingRecordMapper {

    /**
     * Maps a MeterReadingRecord entity to a MeterReadingResponseDto object.
     *
     * @param meterReadingRecord the MeterReadingRecord entity to map
     * @return the corresponding MeterReadingResponseDto object
     */
    @Mapping(target = "deviceName", expression = "java(findDeviceName(meterReadingRecord.getDeviceId()))")
    @Mapping(target = "userName", expression = "java(findUsername(meterReadingRecord.getUserId()))")
    MeterReadingResponseDto toDto(MeterReadingRecord meterReadingRecord);

    default String findUsername(Long userId) {
        UserService userService = DependencyFactory.findService(UserService.class);
        Optional<User> user = userService.findUserById(userId);
        return user.map(User::getUsername).orElse(null);
    }

    default String findDeviceName(Long deviceId) {
        DeviceService deviceService = DependencyFactory.findService(DeviceService.class);
        Optional<Device> device = deviceService.findById(deviceId);
        return device.map(Device::getName).orElse(null);
    }
}
