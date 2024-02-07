package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * The MetricsRecordRepository interface defines methods for managing meter reading records.
 */
public interface MetricsRecordRepository {

    /**
     * Retrieves all meter reading records for a specific user.
     *
     * @param user the user for whom to retrieve meter reading records
     * @return a list of meter reading records for the specified user
     */
    List<MeterReadingRecord> findAllByUser(User user);

    /**
     * Retrieves all meter reading records from the repository.
     *
     * @return a list of all meter reading records
     */
    List<MeterReadingRecord> findAll();

    /**
     * Retrieves all meter reading records for a specific user and month.
     *
     * @param user  the user for whom to retrieve meter reading records
     * @param month the month for which to retrieve meter reading records
     * @param year  the year for which to retrieve meter reading records
     * @return a list of meter reading records for the specified user and month
     */
    List<MeterReadingRecord> findAllByUserAndMonth(User user, Month month, Year year);

    /**
     * Retrieves all meter reading records for a specific month and year.
     *
     * @param month the month for which to retrieve meter reading records
     * @param year  the year for which to retrieve meter reading records
     * @return a list of meter reading records for the specified month and year
     */
    List<MeterReadingRecord> findAllByMonth(Month month, Year year);

    /**
     * Retrieves the latest meter reading records from the repository.
     *
     * @return a list of the latest meter reading records
     */
    List<MeterReadingRecord> findLatest();

    /**
     * Retrieves the latest meter reading records for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the latest meter reading records for the specified user
     */
    List<MeterReadingRecord> findLatestForUser(Long userId);

    /**
     * Saves the provided meter reading record to the database.
     *
     * @param record the meter reading record to be saved
     * @return an optional containing the saved meter reading record with its generated ID if the operation is successful,
     *         or an empty optional if saving fails
     */
    Optional<MeterReadingRecord> save(MeterReadingRecord record);
}
