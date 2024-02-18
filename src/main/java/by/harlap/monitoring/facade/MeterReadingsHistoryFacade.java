package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.model.MeterReadingRecord;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for meter readings history operations.
 */
@RequiredArgsConstructor
@Component
public class MeterReadingsHistoryFacade {

    private final MeterReadingsService meterReadingsService;
    private final DeviceService deviceService;
    private final MeterReadingRecordMapper meterReadingRecordMapper;


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
