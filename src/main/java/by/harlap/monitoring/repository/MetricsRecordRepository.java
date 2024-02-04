package by.harlap.monitoring.repository;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * The MetricsRecordRepository interface defines methods for managing meter reading records.
 */
public interface MetricsRecordRepository {

    /**
     * Adds a meter reading record to the repository.
     *
     * @param record The meter reading record to be added.
     */
    void add(MeterReadingRecord record);

    /**
     * Retrieves a list of all meter reading records associated with a specific user.
     *
     * @param user The user for whom meter reading records are to be retrieved.
     * @return A list of MeterReadingRecord objects associated with the specified user.
     */
    List<MeterReadingRecord> findAllByUser(User user);

    /**
     * Retrieves a list of all meter reading records in the repository.
     *
     * @return A list of all MeterReadingRecord objects in the repository.
     */
    List<MeterReadingRecord> findAll();

    /**
     * Retrieves a list of meter reading records associated with a specific user for a given month and year.
     *
     * @param user  The user for whom meter reading records are to be retrieved.
     * @param month The month for which records are to be retrieved.
     * @param year  The year for which records are to be retrieved.
     * @return A list of MeterReadingRecord objects associated with the specified user, month, and year.
     */
    List<MeterReadingRecord> findAllByUserAndMonth(User user, Month month, Year year);

    /**
     * Retrieves a list of meter reading records for a given month and year.
     *
     * @param month The month for which records are to be retrieved.
     * @param year  The year for which records are to be retrieved.
     * @return A list of MeterReadingRecord objects for the specified month and year.
     */
    List<MeterReadingRecord> findAllByMonth(Month month, Year year);

    /**
     * Retrieves a list of the latest meter reading records for all users.
     *
     * @return A list of the latest MeterReadingRecord objects for all users.
     */
    List<MeterReadingRecord> findLatest();

    /**
     * Retrieves a list of the latest meter reading records for a specific user.
     *
     * @param user The user for whom the latest meter reading record is to be retrieved.
     * @return A list containing the latest MeterReadingRecord object for the specified user.
     */
    List<MeterReadingRecord> findLatestForUser(User user);
}
