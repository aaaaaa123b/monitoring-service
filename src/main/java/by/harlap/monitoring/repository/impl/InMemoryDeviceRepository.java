package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.ColdWaterDevice;
import by.harlap.monitoring.model.HeatingDevice;
import by.harlap.monitoring.model.HotWaterDevice;
import by.harlap.monitoring.model.base.AbstractDevice;
import by.harlap.monitoring.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The InMemoryDeviceRepository class provides an in-memory implementation of the DeviceRepository interface.
 * It initializes a list of predefined devices such as HeatingDevice, ColdWaterDevice, and HotWaterDevice.
 */
public class InMemoryDeviceRepository implements DeviceRepository {

    /**
     * The list of devices stored in memory.
     */
    private final List<AbstractDevice> devices;

    /**
     * Initializes the in-memory device repository with predefined devices.
     */
    public InMemoryDeviceRepository() {
        devices = new ArrayList<>();
        devices.add(new HeatingDevice("отопление"));
        devices.add(new ColdWaterDevice("холодная вода"));
        devices.add(new HotWaterDevice("горячая вода"));
    }

    /**
     * Retrieves and returns an unmodifiable list of all devices stored in memory.
     *
     * @return An unmodifiable list of all devices.
     */
    @Override
    public List<AbstractDevice> findAll() {
        return Collections.unmodifiableList(devices);
    }
}