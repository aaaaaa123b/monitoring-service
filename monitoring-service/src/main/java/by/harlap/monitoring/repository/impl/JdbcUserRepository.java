package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.EntityNotFoundException;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Implementation of UserRepository using JDBC for database interactions.
 */
@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final ConnectionManager connectionManager;

    /**
     * Finds a user by their ID in the database.
     *
     * @param id the user's ID
     * @return the user object
     */
    @Override
    public User findById(Long id) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM monitoring_service_schema.users WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));

                return user;
            } else {
                String message = "Пользователь с id %d не найден.".formatted(id);
                throw new EntityNotFoundException(message);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обработке SQL-запроса", e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
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
     * Saves a new user to the database.
     *
     * @param user the user to be saved
     * @return optional containing the saved user or Optional.empty() if not saved
     */
    @Override
    public Optional<User> save(User user) {
        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO monitoring_service_schema.users (username, password, role) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    user.setId(id);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
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
    }

    /**
     * Finds and returns a user by their username.
     *
     * @param username the username of the user to be found
     * @return the found user or Optional.empty() if not found
     */
    @Override
    public User findByUsername(String username) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM monitoring_service_schema.users WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));

                return user;
            } else {
                return null;
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
}
