package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.BaseIntegrationTest;
import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for AuditControllerTest")
class AuditControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("Test should return expected json and status 200 for admin")
    void findAuditSuccessfullyTest() throws Exception {

        final User user = new User(1L, "admin", "admin", Role.ADMIN);
        final UserEventResponseDto firstUserEvent = new UserEventResponseDto(LocalDate.of(2024, 1, 1),2L,"Тестовое действие");
        final UserEventResponseDto secondUserEvent = new UserEventResponseDto(LocalDate.of(2024, 2, 1),2L,"Тестовое действие");

        final List<UserEventResponseDto> response = new ArrayList<>();
        response.add(firstUserEvent);
        response.add(secondUserEvent);

        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        mockMvc.perform(get("/audit")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("Test should return status 401 if no jwt in header")
    void findAuditTestShouldReturnStatus401() throws Exception {
        mockMvc.perform(get("/audit"))
                .andExpect(status().isUnauthorized());
    }
}