package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    private final User user = new User("test", "test", Role.USER);
    private final MeterReadingRecord record = new MeterReadingRecord(user, Map.of(), LocalDate.now());
    private final User admin = new User("admin", "admin", Role.ADMIN);

    @Test
    @DisplayName("Should create meter reading record for user and create such event")
    void testCreateMeterReadingRecord() {
        MeterReadingRecord record = new MeterReadingRecord(user, Map.of(), LocalDate.now());

        meterReadingsService.createMeterReadingRecord(record);

        verify(metricsRecordRepository).add(record);
        verify(auditService).createEvent(user, "Пользователь внёс показания счётчиков");
    }

    @Test
    @DisplayName("Should find all records for user and create such event")
    void testFindAllRecordsForUser() {
        when(metricsRecordRepository.findAllByUser(user)).thenReturn(List.of(record));

        List<MeterReadingRecord> result = meterReadingsService.findAllRecords(user);

        verify(auditService).createEvent(user, "Пользователь запросил полную историю внесений показаний счётчиков");
        verify(metricsRecordRepository).findAllByUser(user);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find records for specified month for admin")
    void testFindRecordsForSpecifiedMonthForAdmin() {
        Month month = Month.FEBRUARY;
        Year year = Year.of(2022);
        when(metricsRecordRepository.findAllByMonth(month, year)).thenReturn(List.of(record));

        List<MeterReadingRecord> result = meterReadingsService.findRecordsForSpecifiedMonth(admin, month, year);

        verify(auditService).createEvent(admin, "Пользователь запросил внесение данных со счётчиков за FEBRUARY месяц 2022 года");
        verify(metricsRecordRepository).findAllByMonth(month, year);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find relevant records for user and create such event")
    void testFindRelevantRecordsForUser() {
        when(metricsRecordRepository.findLatestForUser(user)).thenReturn(List.of(record));

        List<MeterReadingRecord> result = meterReadingsService.findRelevantRecords(user);

        verify(auditService).createEvent(user, "Пользователь запросил последние внесения данных со счётчиков");
        verify(metricsRecordRepository).findLatestForUser(user);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find relevant records for admin")
    void testFindRelevantRecordsForAdmin() {
        when(metricsRecordRepository.findLatest()).thenReturn(List.of(record));

        List<MeterReadingRecord> result = meterReadingsService.findRelevantRecords(admin);
        verify(metricsRecordRepository).findLatest();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should check metric reading record existence")
    void testCheckMetricReadingRecordExistence() {
        List<MeterReadingRecord> records = List.of();
        when(metricsRecordRepository.findAllByUserAndMonth(any(), any(), any())).thenReturn(records);

        boolean result = meterReadingsService.checkMetricReadingRecordExistence(user);

        verify(metricsRecordRepository).findAllByUserAndMonth(eq(user), any(), any());
        assertFalse(result);
    }

}