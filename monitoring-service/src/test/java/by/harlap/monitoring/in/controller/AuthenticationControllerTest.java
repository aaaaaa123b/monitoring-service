package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.BaseIntegrationTest;
import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for AuthenticationControllerTest")
class AuthenticationControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("Test register by post method")
    void registerTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("new", "user123");
        final String token = jwtUtil.createJWT(authenticationUserDto.getUsername(), authenticationUserDto.getPassword());
        final TokenResponseDto expectedResponse = new TokenResponseDto(token);

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("Test authenticate by post method")
    void authorizationTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("user", "user");
        final String token = jwtUtil.createJWT(authenticationUserDto.getUsername(), authenticationUserDto.getPassword());
        final TokenResponseDto expectedResponse = new TokenResponseDto(token);

        mockMvc.perform(post("/auth")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}