package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.*;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meterReadings")
@RequiredArgsConstructor
public class MeterReadingsController {

    private final MeterReadingsFacade meterReadingsFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves meter readings history for the authenticated user.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings history
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> getMeterReadingHistory(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        final List<MeterReadingResponseDto> responseData = meterReadingsFacade.findMeterReadingRecords(activeUser);

        return ResponseEntity.ok(responseData);
    }

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

        final List<MeterReadingResponseDto> responseData = meterReadingsFacade.createMeterReadingRecord(activeUser, meterReadingsDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    /**
     * Retrieves meter readings for the specified month and year for the authenticated user.
     *
     * @param username    the username obtained from the request attribute
     * @param monthString the month for which meter readings are requested
     * @param yearString  the year for which meter readings are requested
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings for the specified month and year
     */
    @GetMapping(value = "/forMonth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> getMeterReadingsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString) {
        final User activeUser = securityUtil.findActiveUser(username);

        final List<MeterReadingResponseDto> responseData = meterReadingsFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);

        return ResponseEntity.ok(responseData);
    }

    /**
     * Retrieves relevant meter readings information for the authenticated user.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the relevant meter readings information
     */
    @GetMapping(value = "/relevant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> getRelevantMeterReadingRecords(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        final List<MeterReadingResponseDto> responseData = meterReadingsFacade.createRelevantMeterReadingResponse(activeUser);

        return ResponseEntity.ok(responseData);
    }
}
