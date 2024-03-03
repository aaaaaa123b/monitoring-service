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

/**
 * Controller class for handling meter reading records.
 */
@RestController
@RequestMapping("/meterReadings")
@RequiredArgsConstructor
public class MeterReadingController implements MeterReadingOpenAPI {

    private final MeterReadingFacade meterReadingFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves meter readings history for the authenticated user.
     */
    @GetMapping
    public List<MeterReadingResponseDto> findMeterReadingRecordsHistory(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.findMeterReadingRecords(activeUser);
    }

    /**
     * Handles requests to input meter readings.
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
     */
    @GetMapping(value = "/forMonth")
    public List<MeterReadingResponseDto> findMeterReadingRecordsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);
    }

    /**
     * Retrieves relevant meter readings information for the authenticated user.
     */
    @GetMapping(value = "/relevant")
    public List<MeterReadingResponseDto> findRelevantMeterReadingRecords(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        return meterReadingFacade.createRelevantMeterReadingResponse(activeUser);
    }
}
