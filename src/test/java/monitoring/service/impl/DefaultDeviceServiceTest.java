package monitoring.service.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.service.impl.DefaultDeviceService;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Tests for DefaultDeviceService")
class DefaultDeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;

    @InjectMocks
    DefaultDeviceService deviceService;

    @Test
    @DisplayName("Should find all available devices")
    void listAvailableDevices() {
        List<Device> mockDevices = Arrays.asList(
                new Device("отопление"),
                new Device("холодная вода"),
                new Device("горячая вода")
        );

        when(deviceRepository.findAll()).thenReturn(mockDevices);

        List<Device> result = deviceService.listAvailableDevices();

        assertNotNull(result);
        assertEquals(mockDevices.size(), result.size());
        assertEquals(mockDevices, result);

        verify(deviceRepository, times(1)).findAll();
    }

}