package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.exception.UnknownDeviceException;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.validator.MeterReadingInputValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade for meter reading input operations.
 */
@RequiredArgsConstructor
@Component
public class MeterReadingInputFacade {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final MeterReadingInputValidator meterReadingInputValidator;


    /**
     * Creates meter reading records for the provided user and data.
     *
     * @param user the user for whom the meter readings are created
     * @param data the data containing meter readings for devices
     * @return a list of meter reading response DTOs representing the created meter readings
     */
    public List<MeterReadingResponseDto> createMeterReadingRecord(User user, CreateMeterReadingsDto data) {
        meterReadingInputValidator.validateMetricsExistence(user);

        final Map<String, Double> valuesByName = data.getDeviceValues();
        final List<MeterReadingResponseDto> meterReadingResponseDtoList = new ArrayList<>();
        final Map<Device, Double> valuesByDevice = new HashMap<>();
        final LocalDate now = LocalDate.now();

        valuesByName.forEach((name, value) ->
                deviceService.findByName(name).ifPresentOrElse(
                device -> {
                    valuesByDevice.put(device, value);

                    final MeterReadingResponseDto dto = new MeterReadingResponseDto();
                    dto.setDeviceId(device.getId());
                    dto.setValue(value);
                    dto.setDate(now);
                    dto.setUserId(user.getId());

                    meterReadingResponseDtoList.add(dto);
                },
                () -> throwUnknownDeviceException(name)
        ));

        meterReadingInputValidator.validateDevicesCount(valuesByDevice);
        meterReadingInputValidator.validateValues(valuesByDevice);

        meterReadingsService.createMeterReadingRecord(user, valuesByDevice, now);

        return meterReadingResponseDtoList;
    }

    private void throwUnknownDeviceException(String name) {
        throw new UnknownDeviceException( "Устройство '%s' не существует".formatted(name));
    }
}
