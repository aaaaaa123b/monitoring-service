package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The JdbcDeviceRepository class implements the DeviceRepository interface
 * and provides JDBC-based operations for managing devices in a database.
 */
@Repository
@RequiredArgsConstructor
public class JdbcDeviceRepository implements DeviceRepository {

    private final ConnectionManager connectionManager;

    /**
     * Finds all devices in the database.
     *
     * @return a list of all devices
     */
    @Override
    public List<Device> findAll() {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM monitoring_service_schema.devices";
        List<Device> devices = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Device device = mapResultSetToDevice(rs);
                devices.add(device);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return devices;
    }

    /**
     * Saves a new device to the database.
     */
    @Override
    public synchronized Optional<Device> save(Device device) {
        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO monitoring_service_schema.devices (name) VALUES (?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, device.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    device.setId(id);
                    return Optional.of(device);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления устройства", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves a device by its ID from the database.
     *
     * @param deviceId the ID of the device
     * @return an Optional containing the device, or Optional.empty() if not found
     */
    @Override
    public Optional<Device> findById(Long deviceId) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM monitoring_service_schema.devices WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, deviceId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final Device device = mapResultSetToDevice(rs);

                return Optional.of(device);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обработке SQL-запроса", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Device> findByName(String deviceName) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM monitoring_service_schema.devices WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, deviceName);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final Device device = mapResultSetToDevice(rs);

                return Optional.of(device);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обработке SQL-запроса", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Device mapResultSetToDevice(ResultSet rs) throws SQLException {
        Device device = new Device();
        device.setId(rs.getLong("id"));
        device.setName(rs.getString("name"));
        return device;
    }

}
