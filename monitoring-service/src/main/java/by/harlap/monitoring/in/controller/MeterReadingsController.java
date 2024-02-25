package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.MeterReadingsFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meterReadings")
@RequiredArgsConstructor
@Tag(name = "Meter readings", description = "Find history/relevant/month meter readings, save meter readings")
public class MeterReadingsController {

    private final MeterReadingsFacade meterReadingsFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves meter readings history for the authenticated user.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings history
     */
    @GetMapping
    public List<MeterReadingResponseDto> findMeterReadingHistory(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingsFacade.findMeterReadingRecords(activeUser);
    }

    /**
     * Handles requests to input meter readings.
     *
     * @param username         the username obtained from the request attribute
     * @param meterReadingsDto the CreateMeterReadingsDto containing data for the meter readings to be input
     * @return ResponseEntity containing the response data for the created meter readings
     */
    @PostMapping
    public List<MeterReadingResponseDto> saveMeterReadingRecords(@RequestAttribute("username") String username, @RequestBody @Valid List<CreateMeterReadingsDto> meterReadingsDto) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.USER);

        return meterReadingsFacade.createMeterReadingRecord(activeUser, meterReadingsDto);
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
    public List<MeterReadingResponseDto> findMeterReadingsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingsFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);
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

        return meterReadingsFacade.createRelevantMeterReadingResponse(activeUser);
    }
}
