package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.service.UserService;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * The MeterReadingsMonthlyInfoController class is responsible for displaying meter readings
 * entered by the active user for a specified month and year. It prompts the user to input the year
 * and month, retrieves the relevant records using the MeterReadingsService, and prints the date
 * of entry and the values for each device in the specified time period.
 */
public class MeterReadingsMonthlyInfoController extends AbstractController {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final UserService userService;

    /**
     * Constructs a new MeterReadingsMonthlyInfoController with the specified initialization data
     * and MeterReadingsService.
     *
     * @param initializationData   the data needed for initializing the controller
     * @param meterReadingsService the MeterReadingsService used for retrieving meter reading records
     * @param userService          the service for handling users
     */
    public MeterReadingsMonthlyInfoController(InitializationData initializationData, MeterReadingsService meterReadingsService, DeviceService deviceService, UserService userService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    /**
     * Displays meter readings entered by the active user for a specified month and year.
     * Prompts the user to input the year and month, retrieves the relevant records
     * using the MeterReadingsService, and prints the date of entry and the values
     * for each device in the specified time period.
     */
    @Override
    public void show() {
        final User user = context.getActiveUser();

        console.print("Введите год внесения показаний");
        final Year year = Year.of(console.readInt());

        console.print("Введите месяц внесения показаний");
        final Month month = Month.of(console.readInt());

        final List<MeterReadingRecord> records = meterReadingsService.findRecordsForSpecifiedMonth(user, month, year);

        for (MeterReadingRecord record : records) {
            User actualUser = userService.findUserById(record.getUserId());
            console.print("Дата внесения показаний для пользователя '%s': %s".formatted(actualUser.getUsername(), record.getDate()));

            deviceService.findById(record.getDeviceId())
                    .ifPresent(device -> {
                        final String message = "Счётчик: %s. Значение счётчика: %f".formatted(device.getName(), record.getValue());
                        console.print(message);
                    });
        }
    }
}
