package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.validator.MeterReadingsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade for meter reading operations.
 */
@RequiredArgsConstructor
@Component
public class MeterReadingFacade {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final MeterReadingRecordMapper meterReadingRecordMapper;
    private final MeterReadingsValidator meterReadingsValidator;

    /**
     * Finds meter reading records for the provided active user.
     *
     * @param activeUser the active user for whom meter readings are retrieved
     * @return a list of MeterReadingResponseDto objects representing the meter reading records
     */
    public List<MeterReadingResponseDto> findMeterReadingRecords(User activeUser) {
        final List<MeterReadingRecord> records = meterReadingsService.findAllRecords(activeUser);

        return formMeterReadingResponse(records);
    }

    /**
     * Creates meter reading responses for the specified month and year for the provided active user.
     *
     * @param activeUser  the active user for whom meter readings are retrieved
     * @param monthString the string representing the month
     * @param yearString  the string representing the year
     * @return a list of MeterReadingResponseDto objects representing the meter readings for the specified month and year
     */
    public List<MeterReadingResponseDto> createMeterReadingResponseSpecifiedByMonth(User activeUser, String monthString, String yearString) {
        meterReadingsValidator.validateMonthAndYearExistence(monthString, yearString);
        final int month = meterReadingsValidator.validateMonth(monthString);
        final int year = meterReadingsValidator.validateYear(yearString);

        final List<MeterReadingRecord> records = meterReadingsService.findRecordsForSpecifiedMonth(activeUser, Month.of(month), Year.of(year));

        return formMeterReadingResponse(records);
    }

    /**
     * Creates relevant meter reading responses for the provided active user.
     *
     * @param activeUser the active user for whom meter readings are retrieved.
     * @return a list of MeterReadingResponseDto objects representing the relevant meter readings.
     */
    public List<MeterReadingResponseDto> createRelevantMeterReadingResponse(User activeUser) {
        final List<MeterReadingRecord> records = meterReadingsService.findRelevantRecords(activeUser);

        return formMeterReadingResponse(records);
    }

    /**
     * Creates meter reading records for the provided user and data.
     *
     * @param user the user for whom the meter readings are created
     * @param data the data containing meter readings for devices
     * @return a list of meter reading response DTOs representing the created meter readings
     */
    public List<MeterReadingResponseDto> createMeterReadingRecord(User user, List<CreateMeterReadingDto> data) {
        meterReadingsValidator.validateMetricsExistence(user);


        final LocalDate now = LocalDate.now();
        final List<MeterReadingRecord> meterReadingRecords = new ArrayList<>();
        final List<MeterReadingResponseDto> meterReadingResponseDtoList = new ArrayList<>();

        for (final CreateMeterReadingDto dto : data) {
            deviceService.findByName(dto.getDeviceName())
                    .ifPresent(device -> {
                        final MeterReadingRecord record = meterReadingRecordMapper.toEntity(dto);
                        record.setUserId(user.getId());
                        record.setDeviceId(device.getId());
                        record.setDate(now);
                        meterReadingRecords.add(record);
                        meterReadingResponseDtoList.add(meterReadingRecordMapper.toDto(record));
                    });
        }

        meterReadingsValidator.validateDevicesCount(data.size());

        meterReadingsService.createMeterReadingRecord(user, meterReadingRecords);
        return meterReadingResponseDtoList;
    }

    private List<MeterReadingResponseDto> formMeterReadingResponse(List<MeterReadingRecord> records) {
        final List<MeterReadingResponseDto> meterReadingResponseDtoList = new ArrayList<>();

        for (final MeterReadingRecord record : records) {
            deviceService.findById(record.getDeviceId())
                    .ifPresent(device -> {
                        final MeterReadingResponseDto dto = meterReadingRecordMapper.toDto(record);
                        meterReadingResponseDtoList.add(dto);
                    });
        }

        return meterReadingResponseDtoList;
    }
}
