package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.BaseIntegrationTest;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for AuthenticationController")
class AuthenticationControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("Test should return expected json and status 201")
    void registerSuccessfullyTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("new", "user123");

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test should return status 400 if data is incorrect")
    void registerWithIncorrectDataTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("u", "u");

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test should return status 401 if user already exist")
    void registerWithExistingUserTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("user", "user");

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("Test should return expected json and status 200")
    void authenticationSuccessfullyTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("user", "user");

        mockMvc.perform(post("/auth")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test should return status 400 if data incorrect")
    void authenticationWithIncorrectDataTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("u", "u");

        mockMvc.perform(post("/auth")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test should return status 401 if user not exist")
    void authenticationWithNotExistingUserTest() throws Exception {
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("not", "user");

        mockMvc.perform(post("/auth")
                        .content(objectMapper.writeValueAsString(authenticationUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}