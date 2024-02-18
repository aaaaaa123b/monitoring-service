package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.facade.MeterReadingsRelevantInfoFacade;
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
 * The MeterReadingsRelevantInfoController class extends BaseController and is responsible for retrieving relevant meter readings information.
 */
@Controller
@RequestMapping("/relevantMeterReadings")
@RequiredArgsConstructor
public class MeterReadingsRelevantInfoController {

    private final MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade;
    private final SecurityUtil securityUtil;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterReadingResponseDto>> doGet(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);

        final List<MeterReadingResponseDto> responseData = meterReadingsRelevantInfoFacade.createRelevantMeterReadingResponse(activeUser);

        return ResponseEntity.ok(responseData);
    }

}
