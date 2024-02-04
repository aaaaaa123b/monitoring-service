package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.MeterReadingsService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * The DefaultMeterReadingsService class implements the MeterReadingsService interface
 * and provides services related to meter readings and records.
 */
@AllArgsConstructor
public class DefaultMeterReadingsService implements MeterReadingsService {

    private final MetricsRecordRepository metricsRecordRepository;

    private final AuditService auditService;

    /**
     * Checks if a metric reading record exists for the specified user in the current month and year.
     *
     * @param user The user for whom to check the existence of a metric reading record.
     * @return True if a record exists, false otherwise.
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
     * Creates a meter reading record and logs the corresponding event.
     *
     * @param record The meter reading record to be created.
     */
    @Override
    public void createMeterReadingRecord(MeterReadingRecord record) {
        auditService.createEvent(record.user(), "Пользователь внёс показания счётчиков");
        metricsRecordRepository.add(record);
    }

    /**
     * Retrieves and returns a list of all meter reading records for the specified user.
     *
     * @param user The user for whom to retrieve the records.
     * @return A list of all meter reading records for the specified user.
     */
    @Override
    public List<MeterReadingRecord> findAllRecords(User user) {
        auditService.createEvent(user, "Пользователь запросил полную историю внесений показаний счётчиков");
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findAllByUser(user);
            case ADMIN -> metricsRecordRepository.findAll();
        };
    }

    /**
     * Retrieves and returns a list of meter reading records for the specified user, month, and year.
     *
     * @param user  The user for whom to retrieve the records.
     * @param month The month for which to retrieve the records.
     * @param year  The year for which to retrieve the records.
     * @return A list of meter reading records for the specified user, month, and year.
     */
    @Override
    public List<MeterReadingRecord> findRecordsForSpecifiedMonth(User user, Month month, Year year) {
        auditService.createEvent(user, "Пользователь запросил внесение данных со счётчиков за %s месяц %s года".formatted(month.toString(), year.toString()));
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findAllByUserAndMonth(user, month, year);
            case ADMIN -> metricsRecordRepository.findAllByMonth(month, year);
        };
    }

    /**
     * Retrieves and returns a list of relevant meter reading records for the specified user or all users (for admin).
     *
     * @param user The user for whom to retrieve the relevant records.
     * @return A list of relevant meter reading records for the specified user or all users (for admin).
     */
    @Override
    public List<MeterReadingRecord> findRelevantRecords(User user) {
        auditService.createEvent(user, "Пользователь запросил последние внесения данных со счётчиков");
        return switch (user.getRole()) {
            case USER -> metricsRecordRepository.findLatestForUser(user);
            case ADMIN -> metricsRecordRepository.findLatest();
        };
    }
}
