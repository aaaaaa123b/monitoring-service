package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DeviceServiceImpl")
class DeviceServiceImplTest {

    @Mock
    DeviceRepository deviceRepository;

    @InjectMocks
    DeviceServiceImpl deviceService;

    @Test
    @DisplayName("Test should find all available devices")
    void listAvailableDevicesTest() {
        List<Device> required = new ArrayList<>();
        Device device = new Device(1L, "холодная вода");
        required.add(device);

        when(deviceRepository.findAll()).thenReturn(required);

        List<Device> actual = deviceService.listAvailableDevices();

        assertEquals(required, actual);
    }

    @Test
    @DisplayName("Test should successfully find device by id")
    void findByIdTest() {
        Device device = new Device(1L, "холодная вода");

        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));

        Optional<Device> actual = deviceService.findById(device.getId());

        assertNotNull(actual);
        assertEquals(Optional.of(device), actual);
    }

    @Test
    @DisplayName("Test should successfully find device by name")
    void findByNameTest() {
        Device device = new Device(1L, "холодная вода");

        when(deviceRepository.findByName(device.getName())).thenReturn(Optional.of(device));

        Optional<Device> actual = deviceService.findByName(device.getName());

        assertNotNull(actual);
        assertEquals(Optional.of(device), actual);
    }

    @Test
    @DisplayName("Test should successfully save device")
    void saveTest() {
        Device device = new Device(1L, "холодная вода");

        when(deviceRepository.save(device)).thenReturn(Optional.of(device));

        Optional<Device> actual = deviceService.save(device);

        assertNotNull(actual);
        assertEquals(Optional.of(device), actual);
    }
}