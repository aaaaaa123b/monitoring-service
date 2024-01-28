package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.MeterReadingsService;

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

    /**
     * The MeterReadingsService used for retrieving meter reading records.
     */
    private final MeterReadingsService meterReadingsService;

    /**
     * Constructs a new MeterReadingsMonthlyInfoController with the specified initialization data
     * and MeterReadingsService.
     *
     * @param initializationData   The data needed for initializing the controller.
     * @param meterReadingsService The MeterReadingsService used for retrieving meter reading records.
     */
    public MeterReadingsMonthlyInfoController(InitializationData initializationData, MeterReadingsService meterReadingsService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
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
            console.print("Дата внесения показаний для пользователя '%s': %s".formatted(record.user().getUsername(), record.date()));
            record.values().forEach((device, value) -> {
                final String message = "Счётчик: %s. Значение счётчика: %f".formatted(device.getName(), value);
                console.print(message);
            });
        }
    }
}
