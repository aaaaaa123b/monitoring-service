package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsHistoryFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The MeterReadingsHistoryController class extends BaseController and is responsible for retrieving meter readings history.
 */
@Controller
@RequestMapping("/meterReadingsHistory")
@RequiredArgsConstructor
public class MeterReadingsHistoryController {

    private final MeterReadingsHistoryFacade meterReadingsHistoryFacade;
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

        final List<MeterReadingResponseDto> responseData = meterReadingsHistoryFacade.findMeterReadingRecords(activeUser);

        return ResponseEntity.ok(responseData);
    }
}
