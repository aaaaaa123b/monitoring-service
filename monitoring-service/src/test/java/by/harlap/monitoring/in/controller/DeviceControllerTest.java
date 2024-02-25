package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.BaseIntegrationTest;
import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for DeviceControllerTest")
class DeviceControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("Test creating device by post method")
    void createDeviceTest() throws Exception {
        final User user = new User(1L, "admin", "admin", Role.ADMIN);
        final CreateDeviceDto createDeviceDto = new CreateDeviceDto("testDevice");
        final DeviceResponseDto deviceResponseDto = new DeviceResponseDto(4L, createDeviceDto.getName());
        final String token = jwtUtil.createJWT(user.getUsername(), user.getPassword());

        mockMvc.perform(post("/devices")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                        .content(objectMapper.writeValueAsString(createDeviceDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(deviceResponseDto)));
    }
}
