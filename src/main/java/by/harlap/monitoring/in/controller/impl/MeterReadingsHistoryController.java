package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsHistoryFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The MeterReadingsHistoryController class extends BaseController and is responsible for retrieving meter readings history.
 */
public class MeterReadingsHistoryController extends BaseController {

    private final MeterReadingsHistoryFacade meterReadingsHistoryFacade;

    /**
     * Constructs a MeterReadingsHistoryController object with the specified MeterReadingsHistoryFacade.
     *
     * @param meterReadingsHistoryFacade the MeterReadingsHistoryFacade object to use for accessing meter readings history
     */
    public MeterReadingsHistoryController(MeterReadingsHistoryFacade meterReadingsHistoryFacade) {
        this.meterReadingsHistoryFacade = meterReadingsHistoryFacade;
    }

    /**
     * Handles HTTP GET requests for retrieving meter readings history.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(request);

        final List<MeterReadingResponseDto> dtoList = meterReadingsHistoryFacade.findMeterReadingRecords(activeUser);

        IOUtil.write(response, dtoList, HttpServletResponse.SC_OK);
    }
}
