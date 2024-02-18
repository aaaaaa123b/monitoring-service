package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.AuditFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Tests for AuditControllerTest")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuditFacade auditFacade;
    @InjectMocks
    private AuditController auditController;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeEach
    public void setUp() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @Test
    @DisplayName("Test get audit by get method")
    void doGetTest() throws IOException {

        User user = new User(1L, "user", "user", Role.ADMIN);
        UserEvent event = new UserEvent(1L, 1L, "Testaction", LocalDate.of(2024, 1, 1));
        UserEventResponseDto userEventResponseDto = new UserEventResponseDto();
        userEventResponseDto.setUserName("user");
        userEventResponseDto.setAction(event.getAction());
        userEventResponseDto.setDate(event.getDate());

        List<UserEventResponseDto> events = new ArrayList<>();
        events.add(userEventResponseDto);

        when(auditFacade.findUserEvents()).thenReturn(events);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(SecurityUtil.findActiveUser(any())).thenReturn(user);

        auditController.doGet(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userName\":\"user\",\"action\":\"Testaction\"}]";

        assertEquals(expectedResponseBody, responseWriter.toString());
    }

    @AfterEach
    public void close() {
        securityUtilMockedStatic.close();
    }
}