package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.AuditFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for AuditControllerTest")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuditFacade auditFacade;

    @InjectMocks
    private AuditController auditController;

    @Mock
    private SecurityUtil securityUtil;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
    }

    @Test
    @DisplayName("Test get audit by get method")
    void doGetTest() throws Exception {
        final User user = new User(1L, "admin", "admin", Role.ADMIN);
        final UserEvent event = new UserEvent(1L, 1L, "TestAction", LocalDate.of(2024, 1, 1));
        final UserEventResponseDto userEventResponseDto = new UserEventResponseDto();
        userEventResponseDto.setUserId(1L);
        userEventResponseDto.setAction(event.getAction());
        userEventResponseDto.setDate(event.getDate());

        final List<UserEventResponseDto> events = new ArrayList<>();
        events.add(userEventResponseDto);

        when(auditFacade.findUserEvents()).thenReturn(events);

        when(securityUtil.findActiveUser(any())).thenReturn(user);

        final String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userId\":1,\"action\":\"TestAction\"}]";

        mockMvc.perform(get("/audit")
                        .requestAttr("username", user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponseBody));
    }
}