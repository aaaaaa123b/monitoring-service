package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.impl.DefaultMeterReadingsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DefaultMeterReadingsService")
class DefaultMeterReadingsServiceTest {

    @Mock
    private MetricsRecordRepository metricsRecordRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private DefaultMeterReadingsService meterReadingsService;

    @Test
    @DisplayName("Should find all records for user and create such event")
    void findAllRecordsForUserTest() {
        User expectedUser = new User(1L, "user", "user", Role.USER);
        MeterReadingRecord expectedRecord = new MeterReadingRecord(1L, 1L, 100.0, LocalDate.of(2024, 1, 1));

        when(metricsRecordRepository.findAllByUser(expectedUser)).thenReturn(List.of(expectedRecord));

        List<MeterReadingRecord> actualRecords = meterReadingsService.findAllRecords(expectedUser);

        verify(auditService).createEvent(expectedUser, "Пользователь запросил полную историю внесений показаний счётчиков");
        verify(metricsRecordRepository).findAllByUser(expectedUser);
        assertThat(actualRecords).hasSize(1);
    }

    @Test
    @DisplayName("Should find records for specified month for admin")
    void findRecordsForSpecifiedMonthForAdminTest() {
        Month month = Month.FEBRUARY;
        Year year = Year.of(2022);
        MeterReadingRecord expectedRecord = new MeterReadingRecord(1L, 1L, 100.0, LocalDate.of(2024, 1, 1));
        User admin = new User(2L, "admin", "admin", Role.ADMIN);

        when(metricsRecordRepository.findAllByMonth(month, year)).thenReturn(List.of(expectedRecord));

        List<MeterReadingRecord> actualRecords = meterReadingsService.findRecordsForSpecifiedMonth(admin, month, year);

        verify(auditService).createEvent(admin, "Пользователь запросил внесение данных со счётчиков за FEBRUARY месяц 2022 года");
        verify(metricsRecordRepository).findAllByMonth(month, year);
        assertThat(actualRecords).hasSize(1);
    }

    @Test
    @DisplayName("Should find relevant records for user and create such event")
    void findRelevantRecordsForUserTest() {
        User expectedUser = new User(1L, "user", "user", Role.USER);
        MeterReadingRecord expectedRecord = new MeterReadingRecord(1L, 1L, 100.0, LocalDate.of(2024, 1, 1));

        when(metricsRecordRepository.findLatestForUser(expectedUser.getId())).thenReturn(List.of(expectedRecord));

        List<MeterReadingRecord> actualRecords = meterReadingsService.findRelevantRecords(expectedUser);

        verify(auditService).createEvent(expectedUser, "Пользователь запросил последние внесения данных со счётчиков");
        verify(metricsRecordRepository).findLatestForUser(expectedUser.getId());
        assertThat(actualRecords).hasSize(1);
    }

    @Test
    @DisplayName("Should find relevant records for admin")
    void findRelevantRecordsForAdminTest() {
        MeterReadingRecord expectedRecord = new MeterReadingRecord(1L, 1L, 100.0, LocalDate.of(2024, 1, 1));
        User admin = new User(2L, "admin", "admin", Role.ADMIN);

        when(metricsRecordRepository.findLatest()).thenReturn(List.of(expectedRecord));

        List<MeterReadingRecord> actualRecords = meterReadingsService.findRelevantRecords(admin);
        assertThat(actualRecords).hasSize(1);
    }

    @Test
    @DisplayName("Should check metric reading record existence")
    void checkMetricReadingRecordExistenceTest() {
        List<MeterReadingRecord> records = List.of();
        User expectedUser = new User(1L, "user", "user", Role.USER);

        when(metricsRecordRepository.findAllByUserAndMonth(any(), any(), any())).thenReturn(records);

        boolean result = meterReadingsService.checkMetricReadingRecordExistence(expectedUser);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should create meter reading record for user and create such event")
    void createMeterReadingRecordTest() {
        Device device = new Device(1L, "холодная вода");
        Map<Device, Double> meterReadings = new HashMap<>();
        meterReadings.put(device, 100.1);
        User expectedUser = new User(1L, "user", "user", Role.USER);
        MeterReadingRecord expectedRecord = new MeterReadingRecord(1L, 1L, 100.0, LocalDate.of(2024, 1, 1));

        when(metricsRecordRepository.save(any())).thenReturn(Optional.of(expectedRecord));

        meterReadingsService.createMeterReadingRecord(expectedUser, meterReadings, LocalDate.of(2024, 1, 1));

        verify(auditService).createEvent(expectedUser, "Пользователь внёс показания счётчиков");
    }
}