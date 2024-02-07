package by.harlap.monitoring.service;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

/**
 * The MeterReadingsService interface defines methods for managing meter readings and records.
 */
public interface MeterReadingsService {

    /**
     * Checks if a metric reading record exists for the specified user in the current month and year.
     *
     * @param user the user for whom to check the existence of a metric reading record
     * @return true if a record exists, false otherwise
     */
    boolean checkMetricReadingRecordExistence(User user);

    /**
     * Retrieves and returns a list of all meter reading records for the specified user.
     *
     * @param user the user for whom to retrieve the records
     * @return a list of all meter reading records for the specified user
     */
    List<MeterReadingRecord> findAllRecords(User user);

    /**
     * Retrieves and returns a list of meter reading records for the specified user, month, and year.
     *
     * @param user  the user for whom to retrieve the records
     * @param month the month for which to retrieve the records
     * @param year  the year for which to retrieve the records
     * @return a list of meter reading records for the specified user, month, and year
     */
    List<MeterReadingRecord> findRecordsForSpecifiedMonth(User user, Month month, Year year);

    /**
     * Retrieves and returns a list of relevant meter reading records for the specified user or all users.
     *
     * @param user the user for whom to retrieve the relevant records
     * @return a list of relevant meter reading records for the specified user or all users
     */
    List<MeterReadingRecord> findRelevantRecords(User user);

    /**
     * Creates a meter reading record for the active user with the provided values at the specified date.
     *
     * @param activeUser the user for whom the meter reading record is being created
     * @param values     a map containing devices as keys and their corresponding meter readings as values
     * @param now        the date when the meter reading record is created
     */
    void createMeterReadingRecord(User activeUser, Map<Device, Double> values, LocalDate now);
}