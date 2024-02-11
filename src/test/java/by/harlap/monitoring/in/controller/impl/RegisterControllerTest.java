package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.user.RegisterUserDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.mapper.UserMapper;
import by.harlap.monitoring.model.User;
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

@DisplayName("Tests for RegisterControllerTest")
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private AuthService authService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Spy
    private static ObjectMapper objectMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RegisterController registerController;

    @Test
    @DisplayName("Test register by post method")
    void doPostTest() throws IOException {
        String createRequestJson = "{\"username\":\"user\",\"password\":\"user\"}";
        User user = new User(1L,"user","user", Role.USER);
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("user");
        registerUserDto.setPassword("user");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));
        String fakeToken = "fakeToken";
        when(authService.register(any())).thenReturn(fakeToken);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(userMapper.toEntity(registerUserDto)).thenReturn(user);
        registerController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "\"fakeToken\"";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }
}