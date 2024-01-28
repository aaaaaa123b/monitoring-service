package monitoring.service.impl;

import by.harlap.monitoring.model.ColdWaterDevice;
import by.harlap.monitoring.model.HeatingDevice;
import by.harlap.monitoring.model.HotWaterDevice;
import by.harlap.monitoring.model.base.AbstractDevice;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.service.impl.DefaultDeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;

    @InjectMocks
    DefaultDeviceService deviceService;

    @Test
    void listAvailableDevices() {
        List<AbstractDevice> mockDevices = Arrays.asList(
                new HeatingDevice("отопление"),
                new ColdWaterDevice("холодная вода"),
                new HotWaterDevice("горячая вода")
        );

        when(deviceRepository.findAll()).thenReturn(mockDevices);

        List<AbstractDevice> result = deviceService.listAvailableDevices();

        assertNotNull(result);
        assertEquals(mockDevices.size(), result.size());
        assertEquals(mockDevices, result);

        verify(deviceRepository, times(1)).findAll();
    }

}