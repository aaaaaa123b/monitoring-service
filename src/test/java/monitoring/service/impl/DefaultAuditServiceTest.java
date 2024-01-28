package monitoring.service.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.service.impl.DefaultAuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultAuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private DefaultAuditService auditService;

    @Test
    void testCreateEvent() {
        User testUser = new User("testUser", "password", Role.USER);
        String message = "Test message";
        auditService.createEvent(testUser, message);

        verify(auditRepository, times(1)).save(any());
    }
}