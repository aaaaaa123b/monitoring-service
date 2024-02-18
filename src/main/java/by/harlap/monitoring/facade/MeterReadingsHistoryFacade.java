package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for meter readings history operations.
 */
public class MeterReadingsHistoryFacade {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final MeterReadingRecordMapper meterReadingRecordMapper;

    /**
     * Constructs a MeterReadingsHistoryFacade with the specified dependencies.
     *
     * @param meterReadingsService     the service responsible for meter readings operations
     * @param deviceService            the service responsible for device operations
     * @param meterReadingRecordMapper the mapper used to map MeterReadingRecord objects to DTOs
     */
    public MeterReadingsHistoryFacade(MeterReadingsService meterReadingsService,DeviceService deviceService ,MeterReadingRecordMapper meterReadingRecordMapper) {
        this.meterReadingRecordMapper = meterReadingRecordMapper;
        this.meterReadingsService = meterReadingsService;
        this.deviceService = deviceService;
    }

    /**
     * Finds meter reading records for the provided active user.
     *
     * @param activeUser the active user for whom meter readings are retrieved
     * @return a list of MeterReadingResponseDto objects representing the meter reading records
     */
    public List<MeterReadingResponseDto> findMeterReadingRecords(User activeUser){
        final List<MeterReadingRecord> records = meterReadingsService.findAllRecords(activeUser);

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
