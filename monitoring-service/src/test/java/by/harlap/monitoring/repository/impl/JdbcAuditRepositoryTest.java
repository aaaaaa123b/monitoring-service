package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.BaseRepositoryTest;
import by.harlap.monitoring.model.UserEvent;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@DisplayName("Tests for JdbcAuditRepositoryTest")
public class JdbcAuditRepositoryTest extends BaseRepositoryTest {

    private final JdbcAuditRepository auditRepository;

    @Test
    @DisplayName("Test should save userEvent successfully")
    void saveAuditEventTest() {
        UserEvent requiredEvent = new UserEvent(4L, 2L, "Тестовое действие", LocalDate.of(2024, 1, 1));

        Optional<UserEvent> actualEvent = auditRepository.save(requiredEvent);

        assertEquals(Optional.of(requiredEvent), actualEvent);
    }

    @Test
    @DisplayName("Test should find all user events successfully")
    void findAllTest() {
        UserEvent requiredEvent = new UserEvent(1L, 2L, "Тестовое действие", LocalDate.of(2024, 1, 1));

        List<UserEvent> actual = auditRepository.findAll();

        assertTrue(actual.contains(requiredEvent));
    }
}
