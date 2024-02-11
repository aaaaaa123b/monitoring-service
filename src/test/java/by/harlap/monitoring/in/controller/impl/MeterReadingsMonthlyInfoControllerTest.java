package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for MeterReadingsMonthlyInfoControllerTest")
@ExtendWith(MockitoExtension.class)
class MeterReadingsMonthlyInfoControllerTest {

    @Mock
    private MeterReadingsService meterReadingsService;
    @Mock
    private MeterReadingRecordMapper meterReadingRecordMapper;
    @Mock
    private UserService userService;
    @Mock
    private DeviceService deviceService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Spy
    private static ObjectMapper objectMapper;

    @InjectMocks
    private MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController;

    @BeforeAll
    public static void init() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Test
    @DisplayName("Test get records specified by month by get method")
    void doGetTest() throws IOException {
        User user = new User(1L, "user", "user", Role.ADMIN);
        Device device = new Device(1L, "device");
        List<MeterReadingRecord> records = new ArrayList<>();
        MeterReadingRecord record = new MeterReadingRecord(1L, 1L, 100.2, LocalDate.of(2024, 1, 1));
        records.add(record);
        MeterReadingResponseDto dto = new MeterReadingResponseDto();
        dto.setUserName(user.getUsername());
        dto.setDeviceName(device.getName());
        dto.setDate(record.getDate());
        dto.setValue(record.getValue());

        when(request.getParameter("month")).thenReturn(String.valueOf(5));
        when(request.getParameter("year")).thenReturn(String.valueOf(2024));
        when(meterReadingRecordMapper.toDto(record)).thenReturn(dto);
        when(meterReadingsService.findRecordsForSpecifiedMonth(user, Month.of(5), Year.of(2024))).thenReturn(records);
        when(deviceService.findById(any())).thenReturn(Optional.of(device));
        when(userService.findUserByUsername(any())).thenReturn(Optional.of(user));

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        meterReadingsMonthlyInfoController.doGet(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userName\":\"user\",\"deviceName\":\"device\",\"value\":100.2}]";

        assertEquals(expectedResponseBody, responseWriter.toString());
    }
}