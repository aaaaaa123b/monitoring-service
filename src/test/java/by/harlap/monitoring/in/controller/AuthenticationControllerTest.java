package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for AuthenticationControllerTest")
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    @DisplayName("Test register by post method")
    void registerTest() throws Exception {
        final String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto();
        authenticationUserDto.setUsername("user");
        authenticationUserDto.setPassword("user");

        final TokenResponseDto tokenResponseDto = new TokenResponseDto("fakeToken");

        when(authenticationFacade.register(authenticationUserDto)).thenReturn(tokenResponseDto);

        final String expectedResponseBody = "{\"token\":\"fakeToken\"}";

        mockMvc.perform(post("/register")
                        .requestAttr("username", authenticationUserDto.getUsername())
                        .content(createRequestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponseBody));
    }

    @Test
    @DisplayName("Test authenticate by post method")
    void authorizationTest() throws Exception {
        final String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";

        final TokenResponseDto fakeToken = new TokenResponseDto("fakeToken");

        when(authenticationFacade.createToken(any())).thenReturn(fakeToken);

        final String expectedResponseBody = "{\"token\":\"fakeToken\"}";

        mockMvc.perform(post("/auth")
                        .content(createRequestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponseBody));
    }
}