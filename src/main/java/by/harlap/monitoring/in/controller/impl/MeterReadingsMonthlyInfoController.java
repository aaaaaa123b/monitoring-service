package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * The MeterReadingsMonthlyInfoController class extends AbstractController and is responsible for retrieving meter readings for a specified month.
 */
@WebServlet(urlPatterns = "/meterReadingsForMonth")
public class MeterReadingsMonthlyInfoController extends AbstractController {

    private MeterReadingsService meterReadingsService;
    private DeviceService deviceService;
    private MeterReadingRecordMapper meterReadingRecordMapper;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        meterReadingRecordMapper = DependencyFactory.findMapper(MeterReadingRecordMapper.class);
        meterReadingsService = DependencyFactory.findService(MeterReadingsService.class);
        deviceService = DependencyFactory.findService(DeviceService.class);
    }

    /**
     * Handles HTTP GET requests for retrieving meter readings for a specified month.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = findActiveUser(request);

        String monthString = request.getParameter("month");
        String yearString = request.getParameter("year");

        validateMonthAndYearExistence(monthString, yearString);
        final int month = validateMonth(monthString);
        final int year = validateYear(yearString);

        final List<MeterReadingResponseDto> responseData = createMeterReadingResponseSpecifiedByMonth(activeUser, month, year);

        write(response, responseData, HttpServletResponse.SC_OK);
    }

    private List<MeterReadingResponseDto> createMeterReadingResponseSpecifiedByMonth(User activeUser, int month, int year) {
        final List<MeterReadingRecord> records = meterReadingsService.findRecordsForSpecifiedMonth(activeUser, Month.of(month), Year.of(year));

        List<MeterReadingResponseDto> meterReadingResponseDtoList = new ArrayList<>();
        for (MeterReadingRecord record : records) {

            deviceService.findById(record.getDeviceId())
                    .ifPresent(device -> {
                        MeterReadingResponseDto dto = meterReadingRecordMapper.toDto(record);
                        meterReadingResponseDtoList.add(dto);
                    });
        }
        return meterReadingResponseDtoList;
    }

    private void validateMonthAndYearExistence(String month, String year) {
        if (month == null || year == null) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Параметры месяц и год не могут быть пустыми");
        }
    }

    private int validateMonth(String monthString) {
        int month;
        try {
            month = Integer.parseInt(monthString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Неправильный формат месяца");
        }
        if (month > 12 || month < 1) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Месяц должен быть задан в диапазоне от 1 до 12");
        }
        return month;
    }

    private int validateYear(String yearString) {
        final int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Неправильный формат года");
        }
        if (year < 1) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Год должен быть положительным числом");
        }
        return year;
    }
}
