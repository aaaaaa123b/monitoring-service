package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.BaseRepositoryTest;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@DisplayName("Tests for JdbcDeviceRepositoryTest")
class JdbcDeviceRepositoryTest extends BaseRepositoryTest {

    private final DeviceRepository deviceRepository;

    @Test
    @DisplayName("Test should save device successfully")
    void saveTest() {
        Device requiredDevice = new Device(4L, "тестовый счётчик");

        Optional<Device> actualDevice = deviceRepository.save(requiredDevice);

        assertEquals(Optional.of(requiredDevice), actualDevice);
    }

    @Test
    @DisplayName("Test should find all devices successfully")
    void findAllTest() {
        Device requiredDevice = new Device(3L, "отопление");

        List<Device> actualDevice = deviceRepository.findAll();

        assertTrue(actualDevice.contains(requiredDevice));
    }

    @Test
    @DisplayName("Should find device by id successfully")
    void findByIdTest() {
        Device requiredDevice = new Device(3L, "отопление");

        Optional<Device> actualDevice = deviceRepository.findById(3L);

        assertEquals(Optional.of(requiredDevice), actualDevice);
    }
}