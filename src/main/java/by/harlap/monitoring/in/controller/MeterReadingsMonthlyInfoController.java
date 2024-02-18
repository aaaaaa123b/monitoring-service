package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsMonthlyInfoFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The MeterReadingsMonthlyInfoController class extends BaseController and is responsible for retrieving meter readings for a specified month.
 */
@Controller
@RequestMapping("/meterReadingsForMonth")
@RequiredArgsConstructor
public class MeterReadingsMonthlyInfoController {

    private final MeterReadingsMonthlyInfoFacade monthlyInfoFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves meter readings for the specified month and year for the authenticated user.
     *
     * @param username    the username obtained from the request attribute
     * @param monthString the month for which meter readings are requested
     * @param yearString  the year for which meter readings are requested
     * @return ResponseEntity containing a list of MeterReadingResponseDto representing the meter readings for the specified month and year
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> getMeterReadingsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString) {
        final User activeUser = securityUtil.findActiveUser(username);

        final List<MeterReadingResponseDto> responseData = monthlyInfoFacade.createMeterReadingResponseSpecifiedByMonth(activeUser, monthString, yearString);

        return ResponseEntity.ok(responseData);
    }
}
