package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.BaseIntegrationTest;
import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for MeterReadingControllerTest")
class MeterReadingControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("Test should return expected json and status 200")
    void findHistoryOfMeterReadingsTest() throws Exception {
        final User user = new User(2L, "user", "user", Role.USER);
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        final List<MeterReadingResponseDto> records = new ArrayList<>();

        final MeterReadingResponseDto firstRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 1L, 100.5);
        final MeterReadingResponseDto secondRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 2L, 200.3);
        final MeterReadingResponseDto thirdRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 3L, 150.7);

        records.add(firstRecord);
        records.add(secondRecord);
        records.add(thirdRecord);

        mockMvc.perform(get("/meterReadings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    @Test
    @DisplayName("Test should return status 401 if no jwt in header")
    void findHistoryOfMeterReadingsWithNoHeaderTest() throws Exception {
        mockMvc.perform(get("/meterReadings"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test should return expected json and status 200")
    void findMeterReadingsForMonthTest() throws Exception {
        final User user = new User(2L, "user", "user", Role.USER);
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        final List<MeterReadingResponseDto> records = new ArrayList<>();

        final MeterReadingResponseDto firstRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 1L, 100.5);
        final MeterReadingResponseDto secondRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 2L, 200.3);
        final MeterReadingResponseDto thirdRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 3L, 150.7);

        records.add(firstRecord);
        records.add(secondRecord);
        records.add(thirdRecord);

        String month = String.valueOf(1);
        String year = String.valueOf(2024);

        mockMvc.perform(get("/meterReadings/forMonth")
                        .param("month", month)
                        .param("year", year)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    @Test
    @DisplayName("Test should return status 401 if no jwt in header")
    void findMeterReadingsForMonthWithNoHeaderTest() throws Exception {
        mockMvc.perform(get("/meterReadings/forMonth"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test should return expected json and status 200")
    void findRelevantMeterReadingsTest() throws Exception {
        final User user = new User(2L, "user", "user", Role.USER);
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        final List<MeterReadingResponseDto> records = new ArrayList<>();

        final MeterReadingResponseDto firstRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 1L, 100.5);
        final MeterReadingResponseDto secondRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 2L, 200.3);
        final MeterReadingResponseDto thirdRecord = new MeterReadingResponseDto(LocalDate.of(2024, 1, 1), user.getId(), 3L, 150.7);

        records.add(firstRecord);
        records.add(secondRecord);
        records.add(thirdRecord);

        mockMvc.perform(get("/meterReadings/relevant")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    @Test
    @DisplayName("Test should return status 401 if no jwt in header")
    void findRelevantMeterReadingsWithNoHeaderTest() throws Exception {
        mockMvc.perform(get("/meterReadings/relevant"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test should return status 401 if no jwt in header")
    void saveMeterReadingsWithNoHeaderTest() throws Exception {
        mockMvc.perform(post("/meterReadings"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test should return status 403 for admin")
    void saveMeterReadingsByAdminTest() throws Exception {
        final User user = new User(1L, "admin", "admin", Role.ADMIN);
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        final List<CreateMeterReadingDto> records = new ArrayList<>();

        final CreateMeterReadingDto firstRecord = new CreateMeterReadingDto("холодная вода", 100.5);
        final CreateMeterReadingDto secondRecord = new CreateMeterReadingDto("горячая вода", 100.5);
        final CreateMeterReadingDto thirdRecord = new CreateMeterReadingDto("отопление", 100.5);

        records.add(firstRecord);
        records.add(secondRecord);
        records.add(thirdRecord);

        mockMvc.perform(post("/meterReadings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                        .content(objectMapper.writeValueAsString(records))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test should return status 409 when data is not provided for all devices")
    void saveMeterReadingsWithNotAllDataTest() throws Exception {
        final User user = new User(3L, "another", "another", Role.USER);
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        final List<CreateMeterReadingDto> records = new ArrayList<>();

        final CreateMeterReadingDto firstRecord = new CreateMeterReadingDto("холодная вода", 100.5);
        final CreateMeterReadingDto secondRecord = new CreateMeterReadingDto("горячая вода", 100.5);

        records.add(firstRecord);
        records.add(secondRecord);

        mockMvc.perform(post("/meterReadings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                        .content(objectMapper.writeValueAsString(records))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}