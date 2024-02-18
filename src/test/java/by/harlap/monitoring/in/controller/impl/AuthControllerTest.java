package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.facade.AuthFacade;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Tests for AuthControllerTest")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthFacade authFacade;
    @InjectMocks
    private AuthController authController;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeEach
    public void setUp() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }
    @Test
    @DisplayName("Test authenticate by post method")
    void doPost() throws IOException {
        String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));
        TokenResponseDto fakeToken = new TokenResponseDto("fakeToken");

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(authFacade.createToken(any())).thenReturn(fakeToken);

        authController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "{\"token\":\"fakeToken\"}";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }

    @AfterEach
    public void close() {
        securityUtilMockedStatic.close();
    }
}