package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.util.ConnectionManager;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcAuditRepository class implements the AuditRepository interface
 * and provides JDBC-based operations for managing user audit events in a database.
 */
@AllArgsConstructor
public class JdbcAuditRepository implements AuditRepository {

    private final ConnectionManager connectionManager;

    /**
     * Saves a user audit event to the repository.
     *
     * @param userId the ID of the user
     * @param action the action performed
     * @param date   the date of the audit event
     */
    @Override
    public void save(Long userId, String action, LocalDate date) {
        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO monitoring_service_schema.user_events (user_id, action, date) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, action);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }
    }

    /**
     * Retrieves a list of all user audit events stored in the repository.
     *
     * @return A list containing all user audit events in the repository.
     */
    @Override
    public List<UserEvent> findAll() {
        List<UserEvent> userEvents = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        final String query = "SELECT * FROM monitoring_service_schema.user_events";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UserEvent userEvent = mapResultSetToUserEvent(resultSet);
                userEvents.add(userEvent);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }

        return userEvents;
    }

    private UserEvent mapResultSetToUserEvent(ResultSet resultSet) throws SQLException {
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(resultSet.getLong("user_id"));
        userEvent.setAction(resultSet.getString("action"));
        userEvent.setDate(resultSet.getDate("date").toLocalDate());
        return userEvent;
    }
}
