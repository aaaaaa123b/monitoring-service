package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.facade.RegisterFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for RegisterControllerTest")
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    private MockMvc mockMvc;
    @Mock
    private RegisterFacade registerFacade;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    @DisplayName("Test register by post method")
    void doPostTest() throws Exception {
        final String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";
        final AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto();
        authenticationUserDto.setUsername("user");
        authenticationUserDto.setPassword("user");

        final TokenResponseDto tokenResponseDto = new TokenResponseDto("fakeToken");

        when(registerFacade.register(authenticationUserDto)).thenReturn(tokenResponseDto);

        final String expectedResponseBody = "{\"token\":\"fakeToken\"}";

        mockMvc.perform(post("/register")
                        .requestAttr("username", authenticationUserDto.getUsername())
                        .content(createRequestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponseBody));
    }
}