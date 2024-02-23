package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.model.MeterReadingRecord;
import org.mapstruct.Mapper;

/**
 * The MeterReadingRecordMapper interface provides methods for mapping between MeterReadingRecord entities and MeterReadingResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface MeterReadingRecordMapper {

    /**
     * Maps a MeterReadingRecord entity to a MeterReadingResponseDto object.
     *
     * @param meterReadingRecord the MeterReadingRecord entity to map
     * @return the corresponding MeterReadingResponseDto object
     */
    MeterReadingResponseDto toDto(MeterReadingRecord meterReadingRecord);
}
