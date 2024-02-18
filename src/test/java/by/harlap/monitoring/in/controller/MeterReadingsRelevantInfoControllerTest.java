package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingsRelevantInfoFacade;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for MeterReadingsRelevantInfoControllerTest")
@ExtendWith(MockitoExtension.class)
class MeterReadingsRelevantInfoControllerTest {

    @Mock
    private MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade;

    @InjectMocks
    private MeterReadingsRelevantInfoController meterReadingsRelevantInfoController;

    @Mock
    private SecurityUtil securityUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(meterReadingsRelevantInfoController).build();
    }

    @Test
    @DisplayName("Test get relevant records by get method")
    void doGetTest() throws Exception {
        final User user = new User(1L, "user", "user", Role.ADMIN);
        final List<MeterReadingResponseDto> records = new ArrayList<>();

        final MeterReadingResponseDto dto = new MeterReadingResponseDto();
        dto.setDeviceId(1L);
        dto.setUserId(user.getId());
        dto.setDate(LocalDate.of(2024, 1, 1));
        dto.setValue(100.2);

        records.add(dto);

        when(securityUtil.findActiveUser(user.getUsername())).thenReturn(user);
        when(meterReadingsRelevantInfoFacade.createRelevantMeterReadingResponse(user)).thenReturn(records);

        final String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userId\":1,\"deviceId\":1,\"value\":100.2}]";

        mockMvc.perform(get("/relevantMeterReadings")
                        .requestAttr("username", user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponseBody));
    }
}