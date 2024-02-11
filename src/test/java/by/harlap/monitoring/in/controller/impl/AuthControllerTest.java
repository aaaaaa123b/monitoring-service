package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.user.AuthenticateUserDto;
import by.harlap.monitoring.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for AuthControllerTest")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Spy
    private static ObjectMapper objectMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Test authenticate by post method")
    void doPost() throws IOException {
        String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";

        AuthenticateUserDto authenticateUserDto = new AuthenticateUserDto();
        authenticateUserDto.setUsername("user");
        authenticateUserDto.setPassword("user");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));
        String fakeToken = "fakeToken";
        when(authService.login(any(),any())).thenReturn(fakeToken);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        authController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "\"fakeToken\"";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }
}