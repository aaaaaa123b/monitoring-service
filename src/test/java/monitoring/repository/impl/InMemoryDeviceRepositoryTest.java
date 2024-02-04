package monitoring.repository.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.impl.InMemoryDeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for InMemoryDeviceRepository")
class InMemoryDeviceRepositoryTest {

    private final DeviceRepository deviceRepository = new InMemoryDeviceRepository();

    @Test
    @DisplayName("Should successfully return all devices from repository")
    void findAll() {
        List<Device> required = new ArrayList<>();
        required.add(new Device("отопление"));
        required.add(new Device("холодная вода"));
        required.add(new Device("горячая вода"));

        List<Device> actual = deviceRepository.findAll();

        assertEquals(actual,required);
    }
}