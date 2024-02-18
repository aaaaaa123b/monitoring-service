package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingsMonthlyInfoFacade;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Tests for MeterReadingsMonthlyInfoControllerTest")
@ExtendWith(MockitoExtension.class)
class MeterReadingsMonthlyInfoControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private MeterReadingsMonthlyInfoFacade meterReadingsMonthlyInfoFacade;

    @InjectMocks
    private MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeAll
    public static void init() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @Test
    @DisplayName("Test get records specified by month by get method")
    void doGetTest() throws IOException {
        User user = new User(1L, "user", "user", Role.ADMIN);
        List<MeterReadingResponseDto> records = new ArrayList<>();

        MeterReadingResponseDto dto = new MeterReadingResponseDto();
        dto.setUserName(user.getUsername());
        dto.setDeviceName("device");
        dto.setDate(LocalDate.of(2024, 1, 1));
        dto.setValue(100.2);

        records.add(dto);

        when(request.getParameter("month")).thenReturn(String.valueOf(5));
        when(request.getParameter("year")).thenReturn(String.valueOf(2024));

        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(SecurityUtil.findActiveUser(request)).thenReturn(user);
        when(meterReadingsMonthlyInfoFacade.createMeterReadingResponseSpecifiedByMonth(user, "5", "2024")).thenReturn(records);
        meterReadingsMonthlyInfoController.doGet(request, response);

        verify(response).getWriter();
        String expectedResponseBody = "[{\"date\":\"2024-01-01\",\"userName\":\"user\",\"deviceName\":\"device\",\"value\":100.2}]";

        assertEquals(expectedResponseBody, responseWriter.toString());
    }

    @AfterAll
    public static void close() {
        securityUtilMockedStatic.close();
    }
}