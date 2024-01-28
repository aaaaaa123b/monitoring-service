package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.MeterReadingsService;

import java.util.List;

/**
 * The MeterReadingsHistoryController class is responsible for displaying the history of meter readings
 * for the currently active user. It retrieves the meter reading records using the MeterReadingsService
 * and prints the date of entry and the values for each device in the user's history.
 */
public class MeterReadingsHistoryController extends AbstractController {

    /**
     * The MeterReadingsService used for retrieving meter reading records.
     */
    private final MeterReadingsService meterReadingsService;

    /**
     * Constructs a new MeterReadingsHistoryController with the specified initialization data and MeterReadingsService.
     *
     * @param initializationData The data needed for initializing the controller.
     * @param meterReadingsService The MeterReadingsService used for retrieving meter reading records.
     */
    public MeterReadingsHistoryController(InitializationData initializationData, MeterReadingsService meterReadingsService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
    }

    /**
     * Displays the history of meter readings for the currently active user.
     * Retrieves the meter reading records using the MeterReadingsService and prints the date of entry
     * and the values for each device in the user's history.
     */
    @Override
    public void show() {
        final User user = context.getActiveUser();

        final List<MeterReadingRecord> records = meterReadingsService.findAllRecords(user);

        for (MeterReadingRecord record : records) {
            console.print("Дата внесения показаний для пользователя '%s': %s".formatted(record.user().getUsername(), record.date()));
            record.values().forEach((device, value) -> {
                final String message = "Счётчик: %s. Значение счётчика: %f".formatted(device.getName(), value);
                console.print(message);
            });
        }
    }
}
