package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.RegisterFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Tests for RegisterControllerTest")
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RegisterFacade registerFacade;

    @InjectMocks
    private RegisterController registerController;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeAll
    public static void init() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @Test
    @DisplayName("Test register by post method")
    void doPostTest() throws IOException {

        String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";
        User user = new User(1L, "user", "user", Role.USER);
        AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto();
        authenticationUserDto.setUsername("user");
        authenticationUserDto.setPassword("user");

        TokenResponseDto tokenResponseDto = new TokenResponseDto("fakeToken");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));
        when(registerFacade.register(authenticationUserDto)).thenReturn(tokenResponseDto);
        when(SecurityUtil.findActiveUser(request)).thenReturn(user);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        registerController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "{\"token\":\"fakeToken\"}";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }

    @AfterAll
    public static void close() {
        securityUtilMockedStatic.close();
    }
}