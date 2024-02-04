package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;

import java.util.List;

/**
 * The MeterReadingsRelevantInfoController class is responsible for displaying relevant meter readings
 * for the active user. It retrieves the relevant records using the MeterReadingsService and prints
 * the date of entry and the values for each device in the relevant time period.
 */
public class MeterReadingsRelevantInfoController extends AbstractController {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;

    /**
     * Constructs a new MeterReadingsRelevantInfoController with the specified initialization data
     * and MeterReadingsService.
     *
     * @param initializationData   The data needed for initializing the controller.
     * @param meterReadingsService The MeterReadingsService used for retrieving meter reading records.
     */
    public MeterReadingsRelevantInfoController(InitializationData initializationData, MeterReadingsService meterReadingsService, DeviceService deviceService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
        this.deviceService = deviceService;
    }

    /**
     * Displays relevant meter readings for the active user. Retrieves the relevant records
     * using the MeterReadingsService and prints the date of entry and the values
     * for each device in the relevant time period.
     */
    @Override
    public void show() {
        final User user = context.getActiveUser();

        final List<MeterReadingRecord> records = meterReadingsService.findRelevantRecords(user);

        for (MeterReadingRecord record : records) {
            console.print("Дата внесения показаний для пользователя '%s': %s".formatted(user.getUsername(), record.getDate()));

            deviceService.findById(record.getDeviceId())
                    .ifPresent(device -> {
                        final String message = "Счётчик: %s. Значение счётчика: %f".formatted(device.getName(), record.getValue());
                        console.print(message);
                    });
        }
    }
}
