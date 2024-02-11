package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
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
import java.util.ArrayList;
import java.util.List;

/**
 * The MeterReadingsRelevantInfoController class extends AbstractController and is responsible for retrieving relevant meter readings information.
 */
@WebServlet(urlPatterns = "/relevantMeterReadings")
public class MeterReadingsRelevantInfoController extends AbstractController {

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
     * Handles HTTP GET requests for retrieving relevant meter readings information.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = findActiveUser(request);

        final List<MeterReadingResponseDto> responseData = createRelevantMeterReadingResponse(activeUser);

        write(response, responseData, HttpServletResponse.SC_OK);
    }

    private List<MeterReadingResponseDto> createRelevantMeterReadingResponse(User activeUser) {
        final List<MeterReadingRecord> records = meterReadingsService.findRelevantRecords(activeUser);
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

}
