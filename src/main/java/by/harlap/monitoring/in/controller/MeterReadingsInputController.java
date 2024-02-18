package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingInputFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The MeterReadingsInputController class extends BaseController and is responsible for handling meter readings input requests.
 */
@Controller
@RequestMapping("/inputMeterReadings")
@RequiredArgsConstructor
public class MeterReadingsInputController {

    private final MeterReadingInputFacade meterReadingInputFacade;
    private final SecurityUtil securityUtil;

    /**
     * Handles requests to input meter readings.
     *
     * @param username         the username obtained from the request attribute
     * @param meterReadingsDto the CreateMeterReadingsDto containing data for the meter readings to be input
     * @return ResponseEntity containing the response data for the created meter readings
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> inputMeterReadingRecords(@RequestAttribute("username") String username, @RequestBody CreateMeterReadingsDto meterReadingsDto) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.USER);

        final List<MeterReadingResponseDto> responseData = meterReadingInputFacade.createMeterReadingRecord(activeUser, meterReadingsDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }
}


