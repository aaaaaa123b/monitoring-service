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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DefaultDeviceService")
class DefaultDeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;

    @InjectMocks
    DefaultDeviceService deviceService;

    @Test
    @DisplayName("Should find all available devices")
    void listAvailableDevicesTest() {
        List<Device> required = new ArrayList<>();
        Device device = new Device(1L, "холодная вода");
        required.add(device);

        when(deviceRepository.findAll()).thenReturn(required);

        List<Device> actual = deviceService.listAvailableDevices();

        assertEquals(required,actual);
    }

}