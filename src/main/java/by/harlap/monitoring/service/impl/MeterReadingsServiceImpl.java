package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.annotations.Auditable;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.service.MeterReadingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

/**
 * The MeterReadingsServiceImpl class implements the MeterReadingsService interface
 * and provides services related to meter readings and records.
 */
@Service
@RequiredArgsConstructor
public class MeterReadingsServiceImpl implements MeterReadingsService {

    private final MetricsRecordRepository metricsRecordRepository;

    /**
     * Checks if a metric reading record exists for the specified user in the current month and year.
     *
     * @param user the user for whom to check the existence of a metric reading record
     * @return true if a record exists, false otherwise
     */
    @Override
    public boolean checkMetricReadingRecordExistence(User user) {
        final LocalDate date = LocalDate.now();
        final Month month = date.getMonth();
        final Year year = Year.now();

        final List<MeterReadingRecord> records = metricsRecordRepository.findAllByUserAndMonth(user, month, year);

        return !records.isEmpty();
    }

    /**
     * Retrieves and returns a list of all meter reading records for the specified user.
     *
     * @param user the user for whom to retrieve the records
     * @return a list of all meter reading records for the specified user
     */

    @Auditable("Пользователь запросил полную историю внесений показаний счётчиков")
    @Override
    public List<MeterReadingRecord> findAllRecords(User user) {
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findAllByUser(user);
            case ADMIN -> metricsRecordRepository.findAll();
        };
    }

    /**
     * Retrieves and returns a list of meter reading records for the specified user, month, and year.
     *
     * @param user  the user for whom to retrieve the records
     * @param month the month for which to retrieve the records
     * @param year  the year for which to retrieve the records
     * @return a list of meter reading records for the specified user, month, and year
     */
    @Auditable("Пользователь запросил внесение данных со счётчиков за конкретный месяц")
    @Override
    public List<MeterReadingRecord> findRecordsForSpecifiedMonth(User user, Month month, Year year) {
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findAllByUserAndMonth(user, month, year);
            case ADMIN -> metricsRecordRepository.findAllByMonth(month, year);
        };
    }

    /**
     * Retrieves and returns a list of relevant meter reading records for the specified user or all users (for admin).
     *
     * @param user the user for whom to retrieve the relevant records
     * @return a list of relevant meter reading records for the specified user or all users (for admin)
     */
    @Auditable("Пользователь запросил последние внесения данных со счётчиков")
    @Override
    public List<MeterReadingRecord> findRelevantRecords(User user) {
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findLatestForUser(user.getId());
            case ADMIN -> metricsRecordRepository.findLatest();
        };
    }

    /**
     * Creates meter reading records for the specified user and devices with the given values at the specified date.
     *
     * @param user   the user who is creating the meter reading records
     * @param values a map containing devices as keys and corresponding meter reading values as values
     * @param now    the date at which the meter reading records are created
     */
    @Auditable("Пользователь внёс показания счётчиков")
    @Override
    public void createMeterReadingRecord(User user, Map<Device, Double> values, LocalDate now) {
        values.forEach((device, value) -> {
            final MeterReadingRecord record = new MeterReadingRecord(user.getId(), device.getId(), value, now);
            metricsRecordRepository.save(record);
        });
    }
}
