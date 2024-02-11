package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.mapper.DeviceMapper;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.UserService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for AddNewDeviceControllerTest")
class AddNewDeviceControllerTest {

    @Mock
    private DeviceService deviceService;
    @Mock
    private UserService userService;
    @Mock
    private DeviceMapper deviceMapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Spy
    private static ObjectMapper objectMapper;

    @InjectMocks
    private AddNewDeviceController addNewDeviceController;

    @Test
    @DisplayName("Test creating device by post method")
    void doPostTest() throws IOException {
        String createRequestJson = "{\"name\": \"testDevice\"}";
        User user = new User(1L, "user", "user", Role.ADMIN);
        Device device = new Device(1L, "testDevice");

        CreateDeviceDto deviceDto = new CreateDeviceDto();
        deviceDto.setName("testDevice");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(createRequestJson)));

        when(userService.findUserByUsername(any())).thenReturn(Optional.of(user));
        when(deviceService.save(any())).thenReturn(Optional.of(device));

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(deviceMapper.toEntity(eq(deviceDto))).thenReturn(device);

        addNewDeviceController.doPost(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "{\"id\":1,\"name\":\"testDevice\"}";

        assertEquals(expectedResponseBody, responseWriter.toString().replaceAll("\\s", ""));
    }
}
