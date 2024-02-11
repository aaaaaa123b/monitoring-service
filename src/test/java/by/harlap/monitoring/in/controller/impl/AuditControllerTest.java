package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.userEvent.UserEventDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.mapper.UserEventMapper;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import by.harlap.monitoring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for AuditControllerTest")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private AuditService auditService;
    @Mock
    private UserEventMapper userEventMapper;
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Spy
    private static ObjectMapper objectMapper;

    @InjectMocks
    private AuditController auditController;

    @BeforeAll
    public static void init() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Test
    @DisplayName("Test get audit by get method")
    void doGetTest() throws IOException {
        User user = new User(1L, "user", "user", Role.ADMIN);
        UserEvent event = new UserEvent(1L, 1L, "Testaction", LocalDate.of(2024, 1, 1));
        UserEventDto userEventDto = new UserEventDto();
        userEventDto.setUserName("user");
        userEventDto.setAction(event.getAction());
        userEventDto.setDate(event.getDate());

        List<UserEvent> events = new ArrayList<>();
        events.add(event);

        when(userEventMapper.toDto(event)).thenReturn(userEventDto);
        when(userService.findUserByUsername(any())).thenReturn(Optional.of(user));
        when(auditService.findAllUserEvents()).thenReturn(events);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        auditController.doGet(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userName\":\"user\",\"action\":\"Testaction\"}]";

        assertEquals(expectedResponseBody, responseWriter.toString());
    }
}