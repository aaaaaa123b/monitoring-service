package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingFacade;
import by.harlap.monitoring.in.controller.openapi.MeterReadingOpenAPI;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meterReadings")
@RequiredArgsConstructor
public class MeterReadingController implements MeterReadingOpenAPI {

    private final MeterReadingFacade meterReadingFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves meter readings history for the authenticated user.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings history
     */
    @GetMapping
    public List<MeterReadingResponseDto> findMeterReadingRecordsHistory(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.findMeterReadingRecords(activeUser);
    }

    /**
     * Handles requests to input meter readings.
     *
     * @param username         the username obtained from the request attribute
     * @param meterReadingsDto the CreateMeterReadingDto containing data for the meter readings to be input
     * @return ResponseEntity containing the response data for the created meter readings
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public List<MeterReadingResponseDto> saveMeterReadingRecords(@RequestAttribute("username") String username, @RequestBody @Valid List<CreateMeterReadingDto> meterReadingsDto) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.USER);

        return meterReadingFacade.createMeterReadingRecord(activeUser, meterReadingsDto);
    }

    /**
     * Retrieves meter readings for the specified month and year for the authenticated user.
     *
     * @param username    the username obtained from the request attribute
     * @param monthString the month for which meter readings are requested
     * @param yearString  the year for which meter readings are requested
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings for the specified month and year
     */
    @GetMapping(value = "/forMonth")
    public List<MeterReadingResponseDto> findMeterReadingRecordsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);
    }

    /**
     * Retrieves relevant meter readings information for the authenticated user.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the relevant meter readings information
     */
    @GetMapping(value = "/relevant")
    public List<MeterReadingResponseDto> findRelevantMeterReadingRecords(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.createRelevantMeterReadingResponse(activeUser);
    }
}
