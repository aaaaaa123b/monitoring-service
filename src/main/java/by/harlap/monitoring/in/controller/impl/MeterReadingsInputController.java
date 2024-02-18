package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingInputFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The MeterReadingsInputController class extends BaseController and is responsible for handling meter readings input requests.
 */
public class MeterReadingsInputController extends BaseController {

    private final MeterReadingInputFacade meterReadingInputFacade;

    /**
     * Constructs a MeterReadingsInputController object with the specified MeterReadingInputFacade.
     *
     * @param meterReadingInputFacade the MeterReadingInputFacade object to use for handling meter readings input
     */
    public MeterReadingsInputController(MeterReadingInputFacade meterReadingInputFacade) {
      this.meterReadingInputFacade = meterReadingInputFacade;
    }

    /**
     * Handles HTTP POST requests for inputting meter readings.
     *
     * @param requestContext the HTTP servlet request
     * @param response       the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest requestContext, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(requestContext);
        SecurityUtil.validateRequiredRole(activeUser, Role.USER);

        final CreateMeterReadingsDto requestData = IOUtil.read(requestContext, CreateMeterReadingsDto.class);

        final List<MeterReadingResponseDto> responseData = meterReadingInputFacade.createMeterReadingRecord(activeUser, requestData);

        IOUtil.write(response, responseData, HttpServletResponse.SC_CREATED);
    }
}


