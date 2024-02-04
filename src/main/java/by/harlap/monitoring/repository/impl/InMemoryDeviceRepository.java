package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The InMemoryDeviceRepository class provides an in-memory implementation of the DeviceRepository interface.
 * It initializes a list of predefined devices such as HeatingDevice, ColdWaterDevice, and HotWaterDevice.
 */
public class InMemoryDeviceRepository implements DeviceRepository {

    private final List<Device> devices;

    /**
     * Initializes the in-memory device repository with predefined devices.
     */
    public InMemoryDeviceRepository() {
        devices = new ArrayList<>();
        devices.add(new Device("отопление"));
        devices.add(new Device("холодная вода"));
        devices.add(new Device("горячая вода"));
    }

    /**
     * Retrieves and returns an unmodifiable list of all devices stored in memory.
     *
     * @return An unmodifiable list of all devices.
     */
    @Override
    public List<Device> findAll() {
        return Collections.unmodifiableList(devices);
    }

    /**
     * Saves a new device if it is not already present in the collection.
     * Prevents duplicates by checking the presence of the device before adding it to the collection.
     *
     * @param device The device to be saved.
     */
    @Override
    public void save(Device device) {
        if (devices.contains(device)) return;

        devices.add(device);
    }
}