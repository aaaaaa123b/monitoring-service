package monitoring.repository.impl;

import by.harlap.monitoring.model.ColdWaterDevice;
import by.harlap.monitoring.model.HeatingDevice;
import by.harlap.monitoring.model.HotWaterDevice;
import by.harlap.monitoring.model.base.AbstractDevice;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.impl.InMemoryDeviceRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryDeviceRepositoryTest {

    private final DeviceRepository deviceRepository = new InMemoryDeviceRepository();

    @Test
    void findAll() {
        List<AbstractDevice> required = new ArrayList<>();
        required.add(new HeatingDevice("отопление"));
        required.add(new ColdWaterDevice("холодная вода"));
        required.add(new HotWaterDevice("горячая вода"));

        List<AbstractDevice> actual = deviceRepository.findAll();

        assertEquals(actual,required);
    }
}