package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MeterReadingsInputController class handles the user input of meter readings.
 * It prompts the user to enter readings for available devices, creates a meter reading record,
 * and stores it using the MeterReadingsService. The controller checks for administrator status,
 * existing records for the current month, and provides appropriate messages to the user.
 */
public class MeterReadingsInputController extends AbstractController {

    private final MeterReadingsService meterReadingsService;

    private final DeviceService deviceService;

    /**
     * Constructs a new MeterReadingsInputController with the specified initialization data,
     * MeterReadingsService, and DeviceService.
     *
     * @param initializationData The data needed for initializing the controller.
     * @param meterReadingsService The MeterReadingsService used for storing and retrieving meter reading records.
     * @param deviceService The DeviceService used for listing available devices.
     */
    public MeterReadingsInputController(InitializationData initializationData, MeterReadingsService meterReadingsService, DeviceService deviceService) {
        super(initializationData);

        this.meterReadingsService = meterReadingsService;
        this.deviceService = deviceService;
    }

    /**
     * Handles the process of user input for meter readings. Prompts the user to enter readings
     * for available devices, creates a meter reading record, and stores it using the MeterReadingsService.
     * Checks for administrator status and existing records for the current month,
     * providing appropriate messages to the user.
     */
    @Override
    public void show() {
        final User activeUser = context.getActiveUser();

        if (activeUser.getRole().equals(Role.ADMIN)) {
            console.print("Вы как администратор не можете добавить показания счётчиков.");
            return;
        }

        if (meterReadingsService.checkMetricReadingRecordExistence(activeUser)) {
            console.print("Вы уже добавили показания счётчиков в этом месяце.");
            return;
        }

        final List<Device> devices = deviceService.listAvailableDevices();

        final Map<Device, Double> values = new HashMap<>();
        devices.forEach(device -> {
            console.print("Введите показания для счётчика '%s'".formatted(device.getName()));
            final double value = console.readDouble();
            values.put(device, value);
        });

        meterReadingsService.createMeterReadingRecord(activeUser, values, LocalDate.now());

        console.print("Показания счётчиков успешно добавлены");
    }
}
