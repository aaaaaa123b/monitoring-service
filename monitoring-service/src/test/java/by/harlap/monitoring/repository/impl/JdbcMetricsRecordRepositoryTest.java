package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.BaseRepositoryTest;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@DisplayName("Tests for JdbcMetricsRecordRepositoryTest")
class JdbcMetricsRecordRepositoryTest extends BaseRepositoryTest {

    private final MetricsRecordRepository metricsRecordRepository;

    @Test
    @DisplayName("Should save meter reading record successfully")
    void saveTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(4L, 3L, 1L, 100.5, LocalDate.of(2023, 1, 1));

        Optional<MeterReadingRecord> actualRecord = metricsRecordRepository.save(requiredRecord);

        assertEquals(Optional.of(requiredRecord), actualRecord);
    }

    @Test
    @DisplayName("Should find all meter reading records for user successfully")
    void findAllByUserTest() {
        User user = new User(2L, "user", "user", Role.USER);
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByUser(user);

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find all meter reading records successfully")
    void findAllTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(1L, 2L, 1L, 100.5, LocalDate.of(2024, 1, 1));

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAll();

        assertTrue(actualRecord.contains(requiredRecord));
    }

    @Test
    @DisplayName("Should find all meter reading records for user in a certain month successfully")
    void findAllByUserAndMonthTest() {
        User user = new User(2L, "user", "user", Role.USER);

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByUserAndMonth(user, Month.JANUARY, Year.of(2024));

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find all meter reading records in a certain month successfully")
    void findAllByMonthTest() {
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findAllByMonth(Month.JANUARY, Year.of(2024));

        assertThat(actualRecord).hasSize(3);
    }

    @Test
    @DisplayName("Should find latest meter reading record successfully")
    void findLatestTest() {
        MeterReadingRecord requiredRecord = new MeterReadingRecord(1L, 2L, 1L, 100.5, LocalDate.of(2024, 1, 1));

        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findLatest();

        assertTrue(actualRecord.contains(requiredRecord));
    }

    @Test
    @DisplayName("Should find latest meter reading record for user successfully")
    void findLatestForUserTest() {
        List<MeterReadingRecord> actualRecord = metricsRecordRepository.findLatestForUser(2L);

        assertThat(actualRecord).hasSize(3);
    }
}