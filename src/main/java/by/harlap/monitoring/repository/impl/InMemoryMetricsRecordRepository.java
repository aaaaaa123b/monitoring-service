package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The InMemoryMetricsRecordRepository class provides an in-memory implementation of the MetricsRecordRepository interface.
 * It stores meter reading records in a list and allows querying records based on different criteria.
 */
public class InMemoryMetricsRecordRepository implements MetricsRecordRepository {

    /**
     * The list to store meter reading records in memory.
     */
    List<MeterReadingRecord> records = new ArrayList<>();

    /**
     * Adds a meter reading record to the list.
     *
     * @param record The meter reading record to be added.
     */
    @Override
    public void add(MeterReadingRecord record) {
        records.add(record);
    }

    /**
     * Retrieves and returns a list of meter reading records for a specific user.
     *
     * @param user The user for whom meter reading records are to be retrieved.
     * @return A list of meter reading records for the specified user.
     */
    @Override
    public List<MeterReadingRecord> findAllByUser(User user) {
        return records.stream()
                .filter(record -> record.user().equals(user))
                .toList();
    }

    /**
     * Retrieves and returns an unmodifiable list of all meter reading records stored in memory.
     *
     * @return An unmodifiable list of all meter reading records.
     */
    @Override
    public List<MeterReadingRecord> findAll() {
        return Collections.unmodifiableList(records);
    }

    /**
     * Retrieves and returns a list of meter reading records for a specific user within a given month and year.
     *
     * @param user  The user for whom meter reading records are to be retrieved.
     * @param month The month for which meter reading records are to be retrieved.
     * @param year  The year for which meter reading records are to be retrieved.
     * @return A list of meter reading records for the specified user and time period.
     */
    @Override
    public List<MeterReadingRecord> findAllByUserAndMonth(User user, Month month, Year year) {
        return findAllByUser(user).stream()
                .filter(record -> monthAndYearMatches(month, year, record.date()))
                .toList();
    }

    /**
     * Retrieves and returns a list of meter reading records for all users within a given month and year.
     *
     * @param month The month for which meter reading records are to be retrieved.
     * @param year  The year for which meter reading records are to be retrieved.
     * @return A list of meter reading records for all users and the specified time period.
     */
    @Override
    public List<MeterReadingRecord> findAllByMonth(Month month, Year year) {
        return findAll().stream()
                .filter(record -> monthAndYearMatches(month, year, record.date()))
                .toList();
    }

    /**
     * Retrieves and returns a list of the latest meter reading records for each user.
     *
     * @return A list of the latest meter reading records for each user.
     */
    @Override
    public List<MeterReadingRecord> findLatest() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        MeterReadingRecord::user,
                        Collectors.maxBy(Comparator.comparing(MeterReadingRecord::date)))
                ).values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    /**
     * Retrieves and returns a list of the latest meter reading records for a specific user.
     *
     * @param user The user for whom the latest meter reading records are to be retrieved.
     * @return A list of the latest meter reading records for the specified user.
     */
    @Override
    public List<MeterReadingRecord> findLatestForUser(User user) {
        final Optional<MeterReadingRecord> optionalRecord = records.stream()
                .filter(record -> record.user().equals(user))
                .max(Comparator.comparing(MeterReadingRecord::date));

        return optionalRecord.map(List::of).orElseGet(List::of);
    }

    /**
     * Checks if the month and year of a given date match the specified month and year.
     *
     * @param month The month to be checked.
     * @param year  The year to be checked.
     * @param date  The date to check against.
     * @return True if the month and year match, false otherwise.
     */
    private boolean monthAndYearMatches(Month month, Year year, LocalDate date) {
        boolean yearsMatches = date.getYear() == year.getValue();
        return yearsMatches && (date.getMonth().equals(month));
    }
}
