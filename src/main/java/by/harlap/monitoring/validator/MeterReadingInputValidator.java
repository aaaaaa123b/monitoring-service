package by.harlap.monitoring.validator;

import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Validator for meter reading input.
 */
@Component
@RequiredArgsConstructor
public class MeterReadingInputValidator {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;

    /**
     * Validates the existence of metric reading records for the user in the current month.
     *
     * @param user the user for whom the metric reading records are checked
     * @throws GenericHttpException if metric reading records already exist for the user in the current month
     */
    public void validateMetricsExistence(User user) {
        if (meterReadingsService.checkMetricReadingRecordExistence(user)) {
            throw new GenericHttpException( "Вы уже добавили показания счётчиков в этом месяце");
        }
    }

    /**
     * Validates the count of devices against the number of provided values.
     *
     * @param values a map containing devices and their corresponding values
     * @throws GenericHttpException if the count of provided values does not match the count of available devices
     */
    public void validateDevicesCount(Map<Device, Double> values) {
        final List<Device> availableDevices = deviceService.listAvailableDevices();
        if (values.size() != availableDevices.size()) {
            throw new GenericHttpException("Количество введенных устройств не соответствует доступным устройствам");
        }
    }

    /**
     * Validates the values provided for the devices.
     *
     * @param values a map containing devices and their corresponding values
     * @throws GenericHttpException if any value is less than or equal to 0
     */
    public void validateValues(Map<Device, Double> values) {
        for (final Double value : values.values()) {
            if (value <= 0) {
                throw new GenericHttpException("Введённые показания должны быть больше 0");
            }
        }
    }
}
