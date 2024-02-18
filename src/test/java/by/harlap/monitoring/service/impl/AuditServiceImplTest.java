package by.harlap.monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for AuditServiceImpl")
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    @DisplayName("Should create an audit event")
    void createEventTest() {
        User testUser = new User(1L, "testUser", "password", Role.USER);
        String message = "Test message";
        UserEvent userEvent = new UserEvent(testUser.getId(), message, LocalDate.of(2024, 1, 1));

        auditService.createEvent(userEvent);

        verify(auditRepository, times(1)).save(any());
    }
}