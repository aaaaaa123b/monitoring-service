package by.harlap.monitoring.service;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * The MeterReadingsService interface defines methods for managing meter readings and records.
 */
public interface MeterReadingsService {

    /**
     * Checks if a metric reading record exists for the specified user in the current month and year.
     *
     * @param user The user for whom to check the existence of a metric reading record.
     * @return True if a record exists, false otherwise.
     */
    boolean checkMetricReadingRecordExistence(User user);

    /**
     * Creates a meter reading record.
     *
     * @param record The meter reading record to be created.
     */
    void createMeterReadingRecord(MeterReadingRecord record);

    /**
     * Retrieves and returns a list of all meter reading records for the specified user.
     *
     * @param user The user for whom to retrieve the records.
     * @return A list of all meter reading records for the specified user.
     */
    List<MeterReadingRecord> findAllRecords(User user);

    /**
     * Retrieves and returns a list of meter reading records for the specified user, month, and year.
     *
     * @param user  The user for whom to retrieve the records.
     * @param month The month for which to retrieve the records.
     * @param year  The year for which to retrieve the records.
     * @return A list of meter reading records for the specified user, month, and year.
     */
    List<MeterReadingRecord> findRecordsForSpecifiedMonth(User user, Month month, Year year);

    /**
     * Retrieves and returns a list of relevant meter reading records for the specified user or all users (for admin).
     *
     * @param user The user for whom to retrieve the relevant records.
     * @return A list of relevant meter reading records for the specified user or all users (for admin).
     */
    List<MeterReadingRecord> findRelevantRecords(User user);
}