package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.util.ConnectionManager;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MetricsRecordRepository using JDBC for database interactions.
 */
@AllArgsConstructor
public class JdbcMetricsRecordRepository implements MetricsRecordRepository {

    private final ConnectionManager connectionManager;

    /**
     * Retrieves all meter reading records for a specific user.
     *
     * @param user the user for whom to retrieve meter reading records
     * @return a list of meter reading records for the specified user
     */
    @Override
    public List<MeterReadingRecord> findAllByUser(User user) {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT * FROM monitoring_service_schema.meter_reading_records WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                    meterReadingRecords.add(meterReadingRecord);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Retrieves all meter reading records from the repository.
     *
     * @return a list of all meter reading records
     */
    @Override
    public List<MeterReadingRecord> findAll() {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT * FROM monitoring_service_schema.meter_reading_records";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                meterReadingRecords.add(meterReadingRecord);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Retrieves all meter reading records for a specific user and month.
     *
     * @param user  the user for whom to retrieve meter reading records
     * @param month the month for which to retrieve meter reading records
     * @param year  the year for which to retrieve meter reading records
     * @return a list of meter reading records for the specified user and month
     */
    @Override
    public List<MeterReadingRecord> findAllByUserAndMonth(User user, Month month, Year year) {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT * FROM monitoring_service_schema.meter_reading_records WHERE user_id = ? AND EXTRACT(MONTH FROM date) = ? AND EXTRACT(YEAR FROM date) = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setInt(2, month.getValue());
            preparedStatement.setInt(3, year.getValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                    meterReadingRecords.add(meterReadingRecord);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Retrieves all meter reading records for a specific month and year.
     *
     * @param month the month for which to retrieve meter reading records
     * @param year  the year for which to retrieve meter reading records
     * @return a list of meter reading records for the specified month and year
     */
    @Override
    public List<MeterReadingRecord> findAllByMonth(Month month, Year year) {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT * FROM monitoring_service_schema.meter_reading_records WHERE EXTRACT(MONTH FROM date) = ? AND EXTRACT(YEAR FROM date) = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, month.getValue());
            preparedStatement.setInt(2, year.getValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                    meterReadingRecords.add(meterReadingRecord);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Retrieves the latest meter reading records from the repository.
     *
     * @return a list of the latest meter reading records
     */
    @Override
    public List<MeterReadingRecord> findLatest() {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT DISTINCT ON (user_id, device_id) * FROM monitoring_service_schema.meter_reading_records ORDER BY user_id, device_id, date DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                meterReadingRecords.add(meterReadingRecord);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Retrieves the latest meter reading records for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the latest meter reading records for the specified user
     */
    @Override
    public List<MeterReadingRecord> findLatestForUser(Long userId) {
        List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT DISTINCT ON (device_id) * FROM monitoring_service_schema.meter_reading_records WHERE user_id = ? ORDER BY device_id, date DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MeterReadingRecord meterReadingRecord = mapResultSetToMeterReadingRecord(resultSet);
                    meterReadingRecords.add(meterReadingRecord);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return meterReadingRecords;
    }

    /**
     * Adds a new meter reading record to the repository.
     */
    @Override
    public void save(MeterReadingRecord record) {
        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO monitoring_service_schema.meter_reading_records (user_id, device_id, value, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, record.getUserId());
            preparedStatement.setLong(2, record.getDeviceId());
            preparedStatement.setDouble(3, record.getValue());
            preparedStatement.setDate(4, Date.valueOf(record.getDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }
    }

    private MeterReadingRecord mapResultSetToMeterReadingRecord(ResultSet resultSet) throws SQLException {
        MeterReadingRecord meterReadingRecord = new MeterReadingRecord();
        meterReadingRecord.setId(resultSet.getLong("id"));
        meterReadingRecord.setUserId(resultSet.getLong("user_id"));
        meterReadingRecord.setDeviceId(resultSet.getLong("device_id"));
        meterReadingRecord.setValue(resultSet.getDouble("value"));
        meterReadingRecord.setDate(resultSet.getDate("date").toLocalDate());
        return meterReadingRecord;
    }
}
