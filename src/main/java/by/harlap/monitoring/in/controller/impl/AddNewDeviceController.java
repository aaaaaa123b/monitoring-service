package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.service.DeviceService;

/**
 * Controller class for adding a new device.
 */
public class AddNewDeviceController extends AbstractController {

    private final DeviceService deviceService ;

    /**
     * Constructor for AddNewDeviceController.
     *
     * @param initializationData The initialization data for the controller.
     * @param deviceService      The service for managing devices.
     */
    public AddNewDeviceController(InitializationData initializationData, DeviceService deviceService) {
        super(initializationData);

        this.deviceService = deviceService;
    }

    /**
     * Displays the prompt to add a new device and saves it using the DeviceService.
     */
    @Override
    public void show() {
        console.print("Введите название нового счётчика");
        final String newDevice = console.readLine();
        deviceService.save(newDevice);
    }
}
