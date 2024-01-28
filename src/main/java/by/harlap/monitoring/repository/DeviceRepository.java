package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.base.AbstractDevice;

import java.util.List;

/**
 * The DeviceRepository interface defines methods for retrieving information about devices.
 */
public interface DeviceRepository {

    /**
     * Retrieves a list of all available devices.
     *
     * @return A list of AbstractDevice objects representing all available devices.
     */
    List<AbstractDevice> findAll();
}