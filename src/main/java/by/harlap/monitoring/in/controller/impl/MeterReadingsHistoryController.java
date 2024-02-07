package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.service.UserService;

import java.util.List;

/**
 * The MeterReadingsHistoryController class is responsible for displaying the history of meter readings
 * for the currently active user. It retrieves the meter reading records using the MeterReadingsService
 * and prints the date of entry and the values for each device in the user's history.
 */
public class MeterReadingsHistoryController extends AbstractController {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final UserService userService;

    /**
     * Constructs a new MeterReadingsHistoryController with the specified initialization data and MeterReadingsService.
     *
     * @param initializationData   the data needed for initializing the controller
     * @param meterReadingsService the MeterReadingsService used for retrieving meter reading records
     * @param userService          the service for handling users
     */
    public MeterReadingsHistoryController(InitializationData initializationData,
                                          MeterReadingsService meterReadingsService,
                                          DeviceService deviceService, UserService userService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
        this.deviceService = deviceService;
        this.userService = userService;
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
