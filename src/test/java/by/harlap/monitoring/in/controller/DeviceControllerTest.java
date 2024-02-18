package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.DeviceFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for DeviceControllerTest")
class DeviceControllerTest {

    private MockMvc mockMvc;
    @Mock
    private DeviceFacade deviceFacade;
    @Mock
    private SecurityUtil securityUtil;
    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
    }

    @Test
    @DisplayName("Test creating device by post method")
    void doPostTest() throws Exception {
        final String createRequestJson = "{\"name\": \"testDevice\"}";
        final User user = new User(1L, "user", "user", Role.ADMIN);
        final DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
        deviceResponseDto.setName("testDevice");
        deviceResponseDto.setId(1L);

        final CreateDeviceDto deviceDto = new CreateDeviceDto();
        deviceDto.setName("testDevice");

        when(securityUtil.findActiveUser(user.getUsername())).thenReturn(user);
        when(deviceFacade.createDevice(deviceDto)).thenReturn(deviceResponseDto);

        final String expectedResponseBody = "{\"id\":1,\"name\":\"testDevice\"}";

        mockMvc.perform(post("/devices")
                        .requestAttr("username", user.getUsername())
                        .content(createRequestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponseBody));
    }
}
