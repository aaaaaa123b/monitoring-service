package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.DeviceFacade;
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


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DeviceControllerTest")
class DeviceControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private DeviceFacade deviceFacade;

    @InjectMocks
    private DeviceController deviceController;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeAll
    public static void init() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @Test
    @DisplayName("Test creating device by post method")
    void doPostTest() throws IOException {
        String createRequestJson = "{\"name\": \"testDevice\"}";
        User user = new User(1L, "user", "user", Role.ADMIN);
        DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
        deviceResponseDto.setName("testDevice");
        deviceResponseDto.setId(1L);

        CreateDeviceDto deviceDto = new CreateDeviceDto();
        deviceDto.setName("testDevice");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));
        when(SecurityUtil.findActiveUser(request)).thenReturn(user);
        when(deviceFacade.createDevice(deviceDto)).thenReturn(deviceResponseDto);

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        deviceController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "{\"id\":1,\"name\":\"testDevice\"}";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }

    @AfterAll
    public static void close() {
        securityUtilMockedStatic.close();
    }
}
