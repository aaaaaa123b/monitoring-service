package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsMonthlyInfoFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The MeterReadingsMonthlyInfoController class extends BaseController and is responsible for retrieving meter readings for a specified month.
 */
public class MeterReadingsMonthlyInfoController extends BaseController {

    private final MeterReadingsMonthlyInfoFacade monthlyInfoFacade;

    /**
     * Constructs a MeterReadingsMonthlyInfoController object with the specified MeterReadingsMonthlyInfoFacade.
     *
     * @param monthlyInfoFacade the MeterReadingsMonthlyInfoFacade object to use for accessing monthly meter readings information
     */
    public MeterReadingsMonthlyInfoController(MeterReadingsMonthlyInfoFacade monthlyInfoFacade) {
        this.monthlyInfoFacade = monthlyInfoFacade;
    }

    /**
     * Handles HTTP GET requests for retrieving meter readings for a specified month.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(request);

        final String monthString = request.getParameter("month");
        final String yearString = request.getParameter("year");

        final List<MeterReadingResponseDto> responseData = monthlyInfoFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);

        IOUtil.write(response, responseData, HttpServletResponse.SC_OK);
    }
}
