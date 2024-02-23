package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The JdbcAuditRepository class implements the AuditRepository interface
 * and provides JDBC-based operations for managing user audit events in a database.
 */
@Repository
@RequiredArgsConstructor
public class JdbcAuditRepository implements AuditRepository {

    private final ConnectionManager connectionManager;

    /**
     * Saves the given user event to the database.
     *
     * @param userEvent the user event to save
     * @return an optional containing the saved user event with its generated ID if successful,
     *         or an empty optional if saving fails
     * @throws RuntimeException if an SQL exception occurs during query execution
     */
    @Override
    public Optional<UserEvent> save(UserEvent userEvent) {
        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO monitoring_service_schema.user_events (user_id, action, date) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userEvent.getUserId());
            preparedStatement.setString(2, userEvent.getAction());
            preparedStatement.setDate(3, Date.valueOf(userEvent.getDate()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    userEvent.setId(id);
                    return Optional.of(userEvent);
                } else {
                    System.out.println("Не удалось добавить userEvent");
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
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
     * Retrieves a list of all user audit events stored in the repository.
     *
     * @return a list containing all user audit events in the repository
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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userEvents;
    }

    private UserEvent mapResultSetToUserEvent(ResultSet resultSet) throws SQLException {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(resultSet.getLong("id"));
        userEvent.setUserId(resultSet.getLong("user_id"));
        userEvent.setAction(resultSet.getString("action"));
        userEvent.setDate(resultSet.getDate("date").toLocalDate());
        return userEvent;
    }
}
