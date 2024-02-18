package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.validator.MeterReadingsMonthlyInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade for monthly meter readings information operations.
 */
@RequiredArgsConstructor
@Component
public class MeterReadingsMonthlyInfoFacade {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final MeterReadingRecordMapper meterReadingRecordMapper;
    private final MeterReadingsMonthlyInfoValidator meterReadingsMonthlyInfoValidator;


    /**
     * Creates meter reading responses for the specified month and year for the provided active user.
     *
     * @param activeUser  the active user for whom meter readings are retrieved
     * @param monthString the string representing the month
     * @param yearString  the string representing the year
     * @return a list of MeterReadingResponseDto objects representing the meter readings for the specified month and year
     */
    public List<MeterReadingResponseDto> createMeterReadingResponseSpecifiedByMonth(User activeUser, String monthString, String yearString) {
        meterReadingsMonthlyInfoValidator.validateMonthAndYearExistence(monthString, yearString);
        final int month = meterReadingsMonthlyInfoValidator.validateMonth(monthString);
        final int year = meterReadingsMonthlyInfoValidator.validateYear(yearString);

        final List<MeterReadingRecord> records = meterReadingsService.findRecordsForSpecifiedMonth(activeUser, Month.of(month), Year.of(year));

        final List<MeterReadingResponseDto> meterReadingResponseDtoList = new ArrayList<>();
        for (MeterReadingRecord record : records) {

            deviceService.findById(record.getDeviceId())
                    .ifPresent(device -> {
                        final MeterReadingResponseDto dto = meterReadingRecordMapper.toDto(record);
                        meterReadingResponseDtoList.add(dto);
                    });
        }

        return meterReadingResponseDtoList;
    }
}
