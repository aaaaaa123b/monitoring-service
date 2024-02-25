package by.harlap.monitoring.validator;

import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Validator for meter reading input.
 */
@Component
@RequiredArgsConstructor
public class MeterReadingsValidator {

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
            throw new GenericHttpException("Вы уже добавили показания счётчиков в этом месяце");
        }
    }

    /**
     * Validates the count of devices against the number of provided values.
     *
     * @throws GenericHttpException if the count of provided values does not match the count of available devices
     */
    public void validateDevicesCount(int size) {
        final List<Device> availableDevices = deviceService.listAvailableDevices();
        if (size != availableDevices.size()) {
            throw new GenericHttpException("Количество введенных устройств не соответствует доступным устройствам");
        }
    }

    /**
     * Validates the existence of month and year parameters.
     *
     * @param month the month parameter
     * @param year  the year parameter
     * @throws GenericHttpException if either month or year is null
     */
    public void validateMonthAndYearExistence(final String month, final String year) {
        if (month == null || year == null) {
            throw new GenericHttpException("Параметры месяц и год не могут быть пустыми");
        }
    }

    /**
     * Validates the format and range of the month.
     *
     * @param monthString the month string to be validated
     * @return the month as an integer
     * @throws GenericHttpException if the month format is incorrect or out of range
     */
    public int validateMonth(final String monthString) {
        final int month;
        try {
            month = Integer.parseInt(monthString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException("Неправильный формат месяца");
        }
        if (month > 12 || month < 1) {
            throw new GenericHttpException("Месяц должен быть задан в диапазоне от 1 до 12");
        }
        return month;
    }

    /**
     * Validates the format and positivity of the year
     *
     * @param yearString the year string to be validated
     * @return the year as an integer
     * @throws GenericHttpException if the year format is incorrect or negative
     */
    public int validateYear(String yearString) {
        final int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException("Неправильный формат года");
        }
        if (year < 1) {
            throw new GenericHttpException("Год должен быть положительным числом");
        }
        return year;
    }
}
