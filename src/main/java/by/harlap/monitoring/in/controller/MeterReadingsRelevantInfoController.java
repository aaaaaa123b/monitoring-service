package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsRelevantInfoFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The MeterReadingsRelevantInfoController class extends BaseController and is responsible for retrieving relevant meter readings information.
 */
public class MeterReadingsRelevantInfoController extends BaseController {

    private final MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade;

    /**
     * Constructs a MeterReadingsRelevantInfoController object with the specified MeterReadingsRelevantInfoFacade.
     *
     * @param meterReadingsRelevantInfoFacade the MeterReadingsRelevantInfoFacade object to use for accessing relevant meter readings information
     */
    public MeterReadingsRelevantInfoController(MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade) {
        this.meterReadingsRelevantInfoFacade = meterReadingsRelevantInfoFacade;
    }

    /**
     * Handles HTTP GET requests for retrieving relevant meter readings information.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(request);

        final List<MeterReadingResponseDto> responseData = meterReadingsRelevantInfoFacade.createRelevantMeterReadingResponse(activeUser);

        IOUtil.write(response, responseData, HttpServletResponse.SC_OK);
    }

}
